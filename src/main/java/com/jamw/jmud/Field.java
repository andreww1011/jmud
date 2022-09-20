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
 * A field is 
 * 
 * @param <F>
 * 
 * @author andreww1011
 */
public interface Field<F extends Field<F>> extends Comparable<F> {
    
    /**
     * 
     * @param <T> 
     */
    interface Factory<T extends Field<T>> {
        T zero();
        T one();
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
    
    public default F power(Exponent exponent) {
        F e = getFactory().of(exponent.numerator());
        if (exponent.denominator() != 1) //magic number
            e = e.divide(getFactory().of(exponent.denominator()));
        return power(e);
    }
    
    public default F logarithm(Scalar base) {
        return logarithm(base.using(getFactory()));
    }
    
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
}
