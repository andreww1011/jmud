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
public interface Field<F extends Field<F>> extends Comparable<F> {
    
    interface Factory<T extends Field<T>> {
        T of(int value);
        T of(String value) throws NumberFormatException;
    }
        
    F.Factory<F> getFactory();
    
    F negate();
    F reciprocal() throws ArithmeticException;
    F add(F f);
    default F subtract(F f) { return this.add(f.negate()); }
    F multiply(F f);
    default F divide(F f) throws ArithmeticException { return this.multiply(f.reciprocal()); }

    F power(F exponent) throws ArithmeticException;
    F logarithm(F base) throws ArithmeticException;

    public default F add(Scalar scalar) {
        return add(scalar.using(getFactory()));
    }
    
    public default F subtract(Scalar scalar) {
        return subtract(scalar.using(getFactory()));
    }
    
    public default F multiply(Scalar scalar) {
        return multiply(scalar.using(getFactory()));
    }
    
    public default F divide(Scalar scalar) {
        return divide(scalar.using(getFactory()));
    }
    
    public default F power(Scalar exponent) {
        return power(exponent.using(getFactory()));
    }
    
    public default F logarithm(Scalar base) {
        return logarithm(base.using(getFactory()));
    }
    
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
}
