package com.github.asufana.waf;

import io.undertow.*;
import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

public class WAFLite {
    private static final Integer DEFAULT_PORT = 8080;
    
    private final Integer port;
    private Undertow server;
    private final Map<String, String> requestMap = new HashMap<String, String>();
    
    public WAFLite() {
        this(DEFAULT_PORT);
    }
    
    public WAFLite(final Integer port) {
        this.port = port;
    }
    
    public WAFLite get(final String path, final String response) {
        requestMap.put(path, response);
        return this;
    }
    
    public Stoppable start() {
        server = Undertow.builder()
                         .addHttpListener(port, "localhost")
                         .setHandler(new HttpHandler() {
                             @Override
                             public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                 final String relativePath = exchange.getRelativePath();
                                 if (requestMap.containsKey(relativePath)) {
                                     exchange.getResponseSender()
                                             .send(requestMap.get(relativePath));
                                 }
                                 else {
                                     exchange.getResponseSender()
                                             .send("Hello World");
                                 }
                                 exchange.getResponseHeaders()
                                         .put(Headers.CONTENT_TYPE,
                                              "text/plain");
                             }
                         })
                         .build();
        server.start();
        
        return new Stoppable() {
            @Override
            public void stop() {
                server.stop();
            }
        };
    }
}
