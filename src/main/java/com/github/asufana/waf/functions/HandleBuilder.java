package com.github.asufana.waf.functions;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

import lombok.*;

import com.github.asufana.waf.interfaces.*;

public class HandleBuilder {
    
    private final Map<Path, RouteAction> pathFunctionMap = new HashMap<>();
    
    public HandleBuilder() {}
    
    public HandleBuilder add(final Path path, final RouteAction function) {
        pathFunctionMap.put(path, function);
        return this;
    }
    
    public HttpHandler build() {
        return new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                final Request request = new Request(exchange);
                final Response response = new Response();
                final String relativePath = exchange.getRelativePath();
                final RouteAction action = pathFunctionMap.get(new Path(relativePath));
                if (action != null) {
                    action.apply(request, response);
                    exchange.getResponseSender().send(response.renderStrings());
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
