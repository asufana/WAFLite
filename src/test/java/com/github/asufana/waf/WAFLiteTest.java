package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.waf.interfaces.*;
import com.github.asufana.waf.testutils.*;

public class WAFLiteTest {
    
    private final Integer port = 8888;
    private final WAFLite waf = new WAFLite(port);
    
    @Test
    public void testServerStartAndStop() throws Exception {
        final Stoppable stoppable = waf.start();
        assertThat(Netstat.isPortOpen(port), is(true));
        
        stoppable.stop();
        assertThat(Netstat.isPortOpen(port), is(false));
    }
    
    @Test
    public void testAddRoute() throws Exception {
        waf.get("/hoge",
                req -> String.format("Hello Hoge! ( Method: %s )", req.method()));
        waf.get("/moge",
                req -> String.format("Hello Moge! ( Method: %s )", req.method()));
        waf.start();
        
        assertThat(Http.get(port, "/hoge"), is("Hello Hoge! ( Method: GET )"));
        assertThat(Http.get(port, "/moge"), is("Hello Moge! ( Method: GET )"));
    }
}
