/*
 * Copyright (c) 2018 to present, Andrew Wagner.  All rights reserved.
  */
package com.jamw.jmud;

/**
 * Dimension is not commensurable with other dimension.
 * @author Andrew
 * @since 1.0
 */
public class IncommensurableDimensionException extends RuntimeException {
    
    public IncommensurableDimensionException() {
        super();
    }
    
    public IncommensurableDimensionException(Throwable cause) {
        super(cause);
    }
    
    public IncommensurableDimensionException(String message) {
        super(message);
    }
    
    public IncommensurableDimensionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IncommensurableDimensionException(String message, Throwable cause, 
            boolean enableSuppression, boolean writeableStackTrace) {
        super(message,cause,enableSuppression, writeableStackTrace);
    }
    
}
