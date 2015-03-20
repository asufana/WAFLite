package com.github.asufana.waf.interfaces;

import io.undertow.server.*;

@FunctionalInterface
public interface ServerFunction {
    HttpServerExchange apply(final HttpServerExchange ex);
}
