package com.github.asufana.waf.functions;

import lombok.*;

@Getter
public class Response {
    
    private String renderStrings;
    
    Response() {}
    
    public void render(final String renderString) {
        renderStrings = renderString;
    }
    
}
