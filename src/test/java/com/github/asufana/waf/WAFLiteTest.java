package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.waf.testutils.*;

public class WAFLiteTest {
    
    private final WAFLite waf = new WAFLite();
    
    @Test
    public void testServerStartAndStop() throws Exception {
        final Stoppable stoppable = waf.start();
        assertThat(Netstat.isPortOpen(8080), is(true));
        
        stoppable.stop();
        assertThat(Netstat.isPortOpen(8080), is(false));
    }
    
    @Test
    public void testAddRoute() throws Exception {
        final String url = "/hoge";
        final String content = "Hello Hoge!";
        waf.get(url, content).start();
        
        assertThat(Http.get(8080, url), is(content));
    }
}
