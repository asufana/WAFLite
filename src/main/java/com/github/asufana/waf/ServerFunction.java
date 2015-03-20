package com.github.asufana.waf;

import io.undertow.server.*;

@FunctionalInterface
public interface ServerFunction {
    HttpServerExchange apply(final HttpServerExchange ex);
}
