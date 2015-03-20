package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

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
        final String url = "/hoge";
        final String content = "Hello Hoge!";
        waf.get(url, ex -> {
            ex.getResponseSender().send(content);
            return ex;
        }).start();
        
        assertThat(Http.get(port, url), is(content));
    }
}
