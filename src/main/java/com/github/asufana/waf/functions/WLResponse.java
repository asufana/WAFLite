package com.github.asufana.waf.functions;

import lombok.*;

@Getter
public class WLResponse {
    
    private String renderStrings;
    
    WLResponse() {}
    
    public void render(final String renderString) {
        renderStrings = renderString;
    }
    
}
