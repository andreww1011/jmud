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
 *
 * @author andreww1011
 */
public interface Expression {
    
    Dimension getDimension();
    
    Expression add(int value, Unit unit) throws IncommensurableDimensionException;
    
    Expression add(String value, Unit unit) throws IncommensurableDimensionException;
    
    Expression add(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> add(T value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> add(Measure<T> measure) throws IncommensurableDimensionException;
    
    Expression add(Expression expression) throws IncommensurableDimensionException;
    
    Expression subtract(int value, Unit unit) throws IncommensurableDimensionException;
    
    Expression subtract(String value, Unit unit) throws IncommensurableDimensionException;
    
    Expression subtract(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> subtract(T value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> subtract(Measure<T> measure) throws IncommensurableDimensionException;
    
    Expression subtract(Expression expression) throws IncommensurableDimensionException;
    
    Expression multiply(int scalar);
    
    Expression multiply(String scalar);
    
    Expression multiply(Scalar scalar);
    
    <T extends Field<T>> Measure<T> multiply(T scalar);
    
    Expression multiply(int value, Unit unit);
    
    Expression multiply(String value, Unit unit);
    
    Expression multiply(Scalar value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    Expression multiply(Expression expression);
    
    Expression divide(int scalar) throws ArithmeticException;
    
    Expression divide(String scalar) throws ArithmeticException;
    
    Expression divide(Scalar scalar) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T scalar) throws ArithmeticException;
    
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    Expression divide(Scalar value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    Expression divide(Expression expression) throws ArithmeticException;
    
    Expression power(Exponent exponent) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> using(T.Factory<T> factory);
}