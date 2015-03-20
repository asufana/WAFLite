package com.github.asufana.waf.exceptions;

import lombok.*;

import org.slf4j.*;

@Getter
public class WAFLiteException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(WAFLiteException.class);
    
    private final Exception exception;
    private final String message;
    
    public WAFLiteException(final String message) {
        this(null, message);
    }
    
    public WAFLiteException(final Exception exception) {
        this(exception, String.format("%s: %s",
                                      exception.getClass().getSimpleName(),
                                      exception.getMessage()));
    }
    
    public WAFLiteException(final Exception exception, final String message) {
        super();
        this.exception = exception;
        this.message = message;
        
        logger.error("WAFLiteException: {}", message);
    }
}
