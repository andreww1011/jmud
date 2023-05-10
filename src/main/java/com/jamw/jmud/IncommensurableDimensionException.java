/*
 * jmud - (J)ava (M)easures, (U)nits, and (D)imensions
 * Copyright (C) 2022 andreww1011
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jamw.jmud;

/**
 * Dimension is not commensurable with another dimension.
 *
 * @author andreww1011
 */
public class IncommensurableDimensionException extends RuntimeException {
    
    /**
     * <p>Constructor for IncommensurableDimensionException.</p>
     */
    public IncommensurableDimensionException() {
        super();
    }
    
    /**
     * <p>Constructor for IncommensurableDimensionException.</p>
     *
     * @param cause a {@link java.lang.Throwable} object
     */
    public IncommensurableDimensionException(Throwable cause) {
        super(cause);
    }
    
    /**
     * <p>Constructor for IncommensurableDimensionException.</p>
     *
     * @param message a {@link java.lang.String} object
     */
    public IncommensurableDimensionException(String message) {
        super(message);
    }
    
    /**
     * <p>Constructor for IncommensurableDimensionException.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param cause a {@link java.lang.Throwable} object
     */
    public IncommensurableDimensionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * <p>Constructor for IncommensurableDimensionException.</p>
     *
     * @param message a {@link java.lang.String} object
     * @param cause a {@link java.lang.Throwable} object
     * @param enableSuppression a boolean
     * @param writeableStackTrace a boolean
     */
    public IncommensurableDimensionException(String message, Throwable cause, 
            boolean enableSuppression, boolean writeableStackTrace) {
        super(message,cause,enableSuppression, writeableStackTrace);
    }
    
}
