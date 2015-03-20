package com.github.asufana.waf.interfaces;

import com.github.asufana.waf.functions.*;

@FunctionalInterface
public interface RouteAction {
    String apply(final Request ex);
}
