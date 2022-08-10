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
public interface Measure<F extends Field<F>> extends Comparable<Measure<F>>{
    
    F getField();
    
    Unit getUnit();
    
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
    
    @Override
    public int compareTo(Measure<F> o) throws IncommensurableDimensionException;
    
    @Override
    default boolean isEqualTo(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) == 0;
    }
    
    @Override
    default boolean isLessThan(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) < 0;
    }
    
    @Override
    default boolean isGreaterThan(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) > 0;
    }
        
    default F.Factory<F> getFactory() {
        return getField().getFactory();
    }
    
    Measure<F> add(int value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> add(String value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> add(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> add(F value, Unit unit) throws IncommensurableDimensionException;
        
    Measure<F> add(Measure<F> measure) throws IncommensurableDimensionException;
    
    Measure<F> add(Expression expression) throws IncommensurableDimensionException;
        
    Measure<F> subtract(int value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> subtract(String value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> subtract(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    Measure<F> subtract(F value, Unit unit) throws IncommensurableDimensionException;
        
    Measure<F> subtract(Measure<F> measure) throws IncommensurableDimensionException;
    
    Measure<F> subtract(Expression expression) throws IncommensurableDimensionException;
        
    Measure<F> multiply(int scalar);
    
    Measure<F> multiply(String scalar);
    
    Measure<F> multiply(Scalar scalar);
    
    Measure<F> multiply(F scalar);
        
    Measure<F> multiply(int value, Unit unit);
    
    Measure<F> multiply(String value, Unit unit);
    
    Measure<F> multiply(Scalar value, Unit unit);
    
    Measure<F> multiply(F value, Unit unit);
        
    Measure<F> multiply(Measure<F> measure);
    
    Measure<F> multiply(Expression expression);
        
    Measure<F> divide(int scalar) throws ArithmeticException;
    
    Measure<F> divide(String scalar) throws ArithmeticException;
    
    Measure<F> divide(Scalar scalar) throws ArithmeticException;
    
    Measure<F> divide(F scalar) throws ArithmeticException;
        
    Measure<F> divide(int value, Unit unit) throws ArithmeticException;
    
    Measure<F> divide(String value, Unit unit) throws ArithmeticException;
    
    Measure<F> divide(Scalar value, Unit unit) throws ArithmeticException;
    
    Measure<F> divide(F value, Unit unit) throws ArithmeticException;
        
    Measure<F> divide(Measure<F> measure) throws ArithmeticException;
    
    Measure<F> divide(Expression expression) throws ArithmeticException;
    
    Measure<F> as(Unit unit) throws IncommensurableDimensionException;
}
