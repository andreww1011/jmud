/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamw.jmud;

/**
 *
 * @author Andrew
 */
public interface Magnitude<F extends Field<F>> extends Comparable<Magnitude<F>>{
    
    F getValue();
    
    Scale getScale();
    
    Measure<F> getMeasure();
        
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
    
    @Override
    public int compareTo(Magnitude<F> o) throws IncommensurableDimensionException;
    
    @Override
    default boolean isEqualTo(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) == 0;
    }
    
    @Override
    default boolean isLessThan(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) < 0;
    }
    
    @Override
    default boolean isGreaterThan(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) > 0;
    }
}
