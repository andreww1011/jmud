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
public interface Scalar {
 
    Scalar negate();
    
    Scalar reciprocal() throws ArithmeticException;
    
    Scalar add(int scalar);
    
    Scalar add(String scalar);
    
    Scalar add(Scalar scalar);
    
    Expression add(Expression expression);
    
    <T extends Field<T>> Measure<T> add(T value);
    
    <T extends Field<T>> Measure<T> add(Measure<T> measure);
    
    Scalar subtract(int scalar);
    
    Scalar subtract(String scalar);
    
    Scalar subtract(Scalar scalar);
    
    Expression subtract(Expression expression);
    
    <T extends Field<T>> Measure<T> subtract(T value);
    
    <T extends Field<T>> Measure<T> subtract(Measure<T> measure);
    
    Scalar multiply(int scalar);
    
    Scalar multiply(String scalar);
    
    Scalar multiply(Scalar scalar);
    
    Expression multiply(int value, Unit unit);
    
    Expression multiply(String value, Unit unit);
    
    Expression multiply(Expression expression);
    
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    Scalar divide(int scalar) throws ArithmeticException;
    
    Scalar divide(String scalar) throws ArithmeticException;
    
    Scalar divide(Scalar scalar) throws ArithmeticException;
    
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    Expression divide(Expression expression) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    Scalar power(int exponent) throws ArithmeticException;
    
    Scalar power(String exponent) throws ArithmeticException;
    
    Scalar power(Scalar exponent) throws ArithmeticException;

    Scalar power(Exponent exponent) throws ArithmeticException;    
    
    Scalar logarithm(int base) throws ArithmeticException;
    
    Scalar logarithm(String base) throws ArithmeticException;
    
    Scalar logarithm(Scalar base) throws ArithmeticException;

    <T extends Field<T>> T using(T.Factory<T> factory);
}
