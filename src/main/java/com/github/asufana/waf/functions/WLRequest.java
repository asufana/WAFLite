package com.github.asufana.waf.functions;

import io.undertow.server.*;
import io.undertow.util.*;

import java.util.*;

public class WLRequest {
    
    private final HttpServerExchange exchange;
    
    public WLRequest(final HttpServerExchange exchange) {
        this.exchange = exchange;
    }
    
    public HttpString method() {
        return exchange.getRequestMethod();
    }
    
    public HeaderMap headers() {
        return exchange.getRequestHeaders();
    }
    
    public Map<String, Deque<String>> parameters() {
        return exchange.getPathParameters();
    }
    
}
