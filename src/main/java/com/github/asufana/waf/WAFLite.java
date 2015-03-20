package com.github.asufana.waf;

import io.undertow.*;
import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

public class WAFLite {
    private static final Integer DEFAULT_PORT = 8080;
    
    private final Integer port;
    private Undertow server;
    private final Map<String, ServerFunction> requestFunctionMap = new HashMap<>();
    
    public WAFLite() {
        this(DEFAULT_PORT);
    }
    
    public WAFLite(final Integer port) {
        this.port = port;
    }
    
    public WAFLite get(final String path, final ServerFunction function) {
        requestFunctionMap.put(path, function);
        return this;
    }
    
    public Stoppable start() {
        server = Undertow.builder()
                         .addHttpListener(port, "localhost")
                         .setHandler(new HttpHandler() {
                             @Override
                             public void handleRequest(HttpServerExchange exchange) throws Exception {
                                 final String relativePath = exchange.getRelativePath();
                                 final ServerFunction function = requestFunctionMap.get(relativePath);
                                 if (function != null) {
                                     exchange = function.apply(exchange);
                                 }
                                 else {
                                     exchange.getResponseSender()
                                             .send("404 NOT FOUND");
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
