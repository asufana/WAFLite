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
                final WLRequest request = new WLRequest(exchange);
                final WLResponse response = new WLResponse();
                final String relativePath = exchange.getRelativePath();
                final RouteAction action = pathFunctionMap.get(new Path(relativePath));
                if (action != null) {
                    action.apply(request, response);
                    exchange.getResponseSender().send(response.renderStrings());
                }
                else {
                    exchange.setResponseCode(404);
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
