package com.github.asufana.waf;

import io.undertow.*;

import com.github.asufana.waf.functions.*;
import com.github.asufana.waf.functions.HandleBuilder.Action;
import com.github.asufana.waf.functions.HandleBuilder.Method;
import com.github.asufana.waf.functions.HandleBuilder.Path;
import com.github.asufana.waf.interfaces.*;

public class WAFLite {
    private static final Integer DEFAULT_PORT = 8080;
    
    private final Integer port;
    private final HandleBuilder handlerBuilder = new HandleBuilder();
    private Undertow server;
    
    public WAFLite() {
        this(DEFAULT_PORT);
    }
    
    public WAFLite(final Integer port) {
        this.port = port;
    }
    
    public WAFLite get(final String path, final RouteAction action) {
        handlerBuilder.add(new Path(path), new Action(action, Method.GET));
        return this;
    }
    
    public Server start() {
        server = Undertow.builder()
                         .addHttpListener(port, "localhost")
                         .setHandler(handlerBuilder.build())
                         .build();
        server.start();
        
        return new Server() {
            @Override
            public void stop() {
                server.stop();
            }
        };
    }
}
