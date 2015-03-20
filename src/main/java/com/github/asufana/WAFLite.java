package com.github.asufana;

import io.undertow.*;
import io.undertow.server.*;
import io.undertow.util.*;

public class WAFLite {
    
    private Undertow server;
    
    public Stoppable start() {
        server = Undertow.builder()
                         .addHttpListener(8080, "localhost")
                         .setHandler(new HttpHandler() {
                             @Override
                             public void handleRequest(final HttpServerExchange exchange) throws Exception {
                                 exchange.getResponseSender()
                                         .send("Hello World");
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
