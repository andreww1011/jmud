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
 * This class has a natural ordering that is inconsistent with equals.
 * @author andreww1011
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
    
    Measure<F> power(Exponent exponent) throws ArithmeticException;
    
    Measure<F> as(Unit unit) throws IncommensurableDimensionException;
}
