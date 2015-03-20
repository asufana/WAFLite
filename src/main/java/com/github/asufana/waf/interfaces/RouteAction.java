package com.github.asufana.waf.interfaces;

import com.github.asufana.waf.functions.*;

@FunctionalInterface
public interface RouteAction {
    void apply(final Request req, final Response res);
}
