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
 * A scalar represents any number that can be transformed to an equivalent member in
 * a particular {@link Field field} at a later point.  The process of transforming a scalar to 
 * a field type is called <i>particularization</i>.
 * 
 * <p>The operations on a scalar do not affect the numerical precision of the 
 * number a scalar represents.  It is only when a scalar is particularized that 
 * precision may be lost due to the numerical implementation of the field.
 * 
 * <p>A scalar provides functionality for a numerical implementation-independent field. 
 * 
 * <p>Implementations must declare whether they are immutable or not.
 * 
 * @author andreww1011
 */
public interface Scalar {
 
    /**
     * Returns the negative of this scalar, <i>-a</i>.
     * <p><i>a + (-a) = 0</i>.
     *
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar negate();
    
    /**
     * Returns the inverse of this scalar, <i>1/a</i>.
     * <p><i>a • (1/a) = 1</i>.
     *
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar reciprocal();
    
    /**
     * Returns a scalar representing the addition 
     * of this scalar and the specified integer number.
     *
     * @param scalar an integer number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar add(int scalar);
    
    /**
     * Returns a scalar representing the addition
     * of this scalar and the number represented by the specified string.
     *
     * @param scalar a string representing a number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar add(String scalar);
    
    /**
     * Returns a scalar representing the addition
     * of this scalar and the specified scalar.
     *
     * @param scalar another scalar.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar add(Scalar scalar);
    
    /**
     * Returns a dimensionless expression representing the
     * addition of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the specified dimensionless expression.
     *
     * @param expression a {@link com.jamw.jmud.Universe#getDimensionless() dimensionless} expression.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression add(Expression expression);
    
    /**
     * Returns a field of type T that is the addition of this scalar particularized
     * to T and the specified field.
     *
     * @param <T> the type of the field.
     * @param value a field of type T.
     * @return a T object
     */
    <T extends Field<T>> T add(T value);
    
    /**
     * Returns a dimensionless measure that is the addition of
     * this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the specified
     * dimensionless measure.
     *
     * @param <T> the type of the field of the measure.
     * @param measure a {@link com.jamw.jmud.Universe#getDimensionless() dimensionless} measure.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> add(Measure<T> measure);
    
    /**
     * Returns a scalar representing the subtraction 
     * of this scalar and the specified integer number.
     *
     * @param scalar an integer number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar subtract(int scalar);
    
    /**
     * Returns a scalar representing the subtraction
     * of this scalar and the number represented by the specified string.
     *
     * @param scalar a string representing a number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar subtract(String scalar);
    
    /**
     * Returns a scalar representing the subtraction
     * of this scalar and the specified scalar.
     *
     * @param scalar another scalar.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar subtract(Scalar scalar);
    
    /**
     * Returns a dimensionless expression representing
     * the subtraction of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the specified dimensionless expression.
     *
     * @param expression a {@link com.jamw.jmud.Universe#getDimensionless() dimensionless} expression.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression subtract(Expression expression);
    
    /**
     * Returns a field of type T that is the subtraction of this scalar particularized
     * to T and the specified field.
     *
     * @param <T> the type of the field.
     * @param value a field of type T.
     * @return a T object
     */
    <T extends Field<T>> T subtract(T value);
    
    /**
     * Returns a dimensionless measure that is the subtraction of
     * this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the specified
     * dimensionless measure.
     *
     * @param <T> the type of the field of the measure.
     * @param measure a {@link com.jamw.jmud.Universe#getDimensionless() dimensionless} measure.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> subtract(Measure<T> measure);
    
    /**
     * Returns a scalar representing the multiplication 
     * of this scalar and the specified integer number.
     *
     * @param scalar an integer number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar multiply(int scalar);
    
    /**
     * Returns a scalar representing the multiplication
     * of this scalar and the number represented by the specified string.
     *
     * @param scalar a string representing a number.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar multiply(String scalar);
    
    /**
     * Returns a scalar representing the multiplication
     * of this scalar and the specified scalar.
     *
     * @param scalar another scalar.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar multiply(Scalar scalar);
    
    /**
     * Returns an expression representing 
     * the multiplication of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the specified integer number and unit.
     *
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression multiply(int value, Unit unit);
    
    /**
     * Returns an expression representing 
     * the multiplication of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the number represented by the specified string and unit.
     *
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression multiply(String value, Unit unit);
    
    /**
     * Returns an expression representing  the 
     * multiplication of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the specified scalar and unit.
     *
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression multiply(Scalar value, Unit unit);
    
    /**
     * Returns an expression representing the multiplication
     * of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the
     * specified expression.
     *
     * @param expression an expression.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression multiply(Expression expression);
    
    /**
     * Returns a measure that is the result of the multiplication of
     * this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the measure
     * represented by the specified field and unit.
     *
     * @param <T> the type of the field.
     * @param value a field of type T.
     * @param unit the unit of the specified field.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    /**
     * Returns a measure that is the result of the multiplication of this scalar
     * with {@link com.jamw.jmud.Universe#getUnitless() no units} and the specified measure.
     *
     * @param <T> the type of the field of the measure.
     * @param measure another measure.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    /**
     * Returns a scalar representing the division 
     * of this scalar and the specified integer number.
     *
     * @param scalar an integer number.
     * @throws java.lang.ArithmeticException if the specified integer is 0.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar divide(int scalar) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the division
     * of this scalar and the number represented by the specified string.
     *
     * @param scalar a string representing a number.
     * @throws java.lang.ArithmeticException if the number represented by the specified string is 0.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar divide(String scalar) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the division
     * of this scalar and the specified scalar.
     *
     * @param scalar another scalar.
     * @throws java.lang.ArithmeticException if the specified scalar is 0.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar divide(Scalar scalar) throws ArithmeticException;
    
    /**
     * Returns an expression representing 
     * the division of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the specified integer number and unit.
     *
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws java.lang.ArithmeticException if the specified integer is 0.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns an expression representing 
     * the division of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the number represented by the specified string and unit.
     *
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws java.lang.ArithmeticException if the number represented by the specified string is 0.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns an expression representing  the 
     * division of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units}
     * and the measure represented by the specified scalar and unit.
     *
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws java.lang.ArithmeticException if the value of the specified scalar is 0.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression divide(Scalar value, Unit unit);
    
    /**
     * Returns an expression representing  the division
     * of this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the
     * specified expression.
     *
     * @param expression an expression.
     * @throws java.lang.ArithmeticException if the value of the specified expression is 0.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    Expression divide(Expression expression) throws ArithmeticException;
    
    /**
     * Returns a measure that is the result of the division of
     * this scalar with {@link com.jamw.jmud.Universe#getUnitless() no units} and the measure
     * represented by the specified field and unit.
     *
     * @param <T> the type of the field.
     * @param value a field of type T.
     * @param unit the unit of the specified field.
     * @throws java.lang.ArithmeticException if the value of the specified field is 0.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns a measure that is the result of the division of this scalar
     * with {@link com.jamw.jmud.Universe#getUnitless() no units} and the specified measure.
     *
     * @param <T> the type of the field of the measure.
     * @param measure another measure.
     * @throws java.lang.ArithmeticException if the value of the specified measure is 0.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the exponentiation 
     * of this scalar and the specified integer exponent.
     *
     * @param exponent an integer
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar power(int exponent) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the exponentiation 
     * of this scalar and the number represented by the specified string.
     *
     * @param exponent a string representing a number.
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar power(String exponent) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the exponentiation of this scalar and
     * the specified scalar exponent.
     *
     * @param exponent another scalar.
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar power(Scalar exponent) throws ArithmeticException;

    /**
     * Returns a scalar representing the exponentiation of this scalar and the
     * specified exponent.
     *
     * @param exponent an exponent.
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar power(Exponent exponent) throws ArithmeticException;    
    
    /**
     * Returns a scalar representing the logarithm of this scalar in the base
     * of the specified integer.
     *
     * @param base an integer.
     * @throws java.lang.ArithmeticException if the result of the logarithm operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar logarithm(int base) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the logarithm of this scalar in the base of
     * the number represented by the specified string.
     *
     * @param base a string representing a number.
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar logarithm(String base) throws ArithmeticException;
    
    /**
     * Returns a scalar representing the logarithm of this scalar in the base of
     * the specified scalar.
     *
     * @param base another scalar.
     * @throws java.lang.ArithmeticException if the result of the power operation is undefined.
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    Scalar logarithm(Scalar base) throws ArithmeticException;

    /**
     * Particularizes this scalar to a field of type T using the specified factory.
     * 
     * @param <T> type of the field to which this scalar is particularized.
     * @param factory factory of the field type to which this scalar is particularized.
     * @return a field of type T representing the same value as this scalar.
     */
    <T extends Field<T>> T using(T.Factory<T> factory);
}
