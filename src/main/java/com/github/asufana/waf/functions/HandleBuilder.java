package com.github.asufana.waf.functions;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

import lombok.*;

import com.github.asufana.waf.interfaces.*;

public class HandleBuilder {
    
    private final Map<Path, Action> pathFunctionMap = new HashMap<>();
    
    public HandleBuilder() {}
    
    public HandleBuilder add(final Path path, final Action action) {
        pathFunctionMap.put(path, action);
        return this;
    }
    
    public HttpHandler build() {
        return new HttpHandler() {
            @Override
            public void handleRequest(final HttpServerExchange exchange) throws Exception {
                final Request request = new Request(exchange);
                final Response response = new Response();
                final String relativePath = exchange.getRelativePath();
                final Action action = pathFunctionMap.get(new Path(relativePath));
                if (action != null
                        && action.method()
                                 .equals(Method.valueOf(request.method()))) {
                    final RouteAction routeAction = action.routeAction();
                    routeAction.apply(request, response);
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
    public static class Action {
        private final RouteAction routeAction;
        private final Method method;
    }
    
    public static enum Method {
        GET,
        POST;
        
        public static Method valueOf(final HttpString httpString) {
            return Arrays.asList(values())
                         .stream()
                         .filter(value -> value.name()
                                               .toUpperCase()
                                               .equals(httpString.toString()
                                                                 .toUpperCase()))
                         .findFirst()
                         .orElseThrow(() -> new RuntimeException("指定のHTTPメソッドはありません: "
                                 + httpString.toString()));
        }
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
