package com.github.asufana.waf.functions;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

import lombok.*;

import com.github.asufana.waf.interfaces.*;

public class HandleBuilder {
    
    private final Map<Path, ServerFunction> pathFunctionMap = new HashMap<>();
    
    public HandleBuilder() {}
    
    public HandleBuilder add(final Path path,
                                    final ServerFunction function) {
        pathFunctionMap.put(path, function);
        return this;
    }
    
    public HttpHandler build() {
        return new HttpHandler() {
            @Override
            public void handleRequest(HttpServerExchange exchange) throws Exception {
                final String relativePath = exchange.getRelativePath();
                final ServerFunction function = pathFunctionMap.get(new Path(relativePath));
                if (function != null) {
                    exchange = function.apply(exchange);
                }
                else {
                    exchange.getResponseSender().send("404 NOT FOUND");
                }
                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE,
                                                  "text/plain");
            }
        };
    }
    
    @Value
    public static class Path {
        private final String path;
        
        public Path(final String path) {
            if (path.startsWith("/")) {
                this.path = path;
            }
            else {
                this.path = "/" + path;
            }
        }
    }
}
