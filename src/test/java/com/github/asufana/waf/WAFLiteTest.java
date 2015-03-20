package com.github.asufana.waf;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.waf.interfaces.*;
import com.github.asufana.waf.testutils.*;
import com.github.asufana.waf.testutils.Http.HttpResponse;

public class WAFLiteTest {
    
    private final Integer port = 8888;
    private final WAFLite waf = new WAFLite(port);
    
    @Test
    public void testServerStartAndStop() throws Exception {
        final Server server = waf.start();
        assertThat(Netstat.isPortOpen(port), is(true));
        
        server.stop();
        assertThat(Netstat.isPortOpen(port), is(false));
    }
    
    @Test
    public void testAddRoute() throws Exception {
        waf.get("/hoge",
                (req, res) -> res.render(String.format("Hello Hoge! ( Method: %s )",
                                                       req.method())));
        waf.get("/moge",
                (req, res) -> res.render(String.format("Hello Moge! ( Method: %s )",
                                                       req.method())));
        final Server server = waf.start();
        
        final HttpResponse res01 = Http.get(port, "/hoge");
        assertThat(res01.code(), is(200));
        assertThat(res01.contents(), is("Hello Hoge! ( Method: GET )"));
        
        final HttpResponse res02 = Http.get(port, "/moge");
        assertThat(res02.code(), is(200));
        assertThat(res02.contents(), is("Hello Moge! ( Method: GET )"));
        
        server.stop();
    }
    
    @Test
    public void testNotFound() throws Exception {
        final Server server = waf.start();
        assertThat(Http.get(port, "/hoge").code(), is(404));
        
        server.stop();
    }
}
