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
 * An expression represents any dimensional value that can be transformed to an 
 * equivalent measure of a particular {@link Field field} at a later point.  The 
 * process of transforming an expression to a measure of a particular field type 
 * is called <i>particularization</i>.
 * 
 * <p>The operations on an expression do not affect the numerical precision of the 
 * dimensional value it represents.  It is only when an expression is particularized that 
 * precision may be lost due to the numerical implementation of the underlying field.
 * 
 * <p>An expression provides functionality for a numerical implementation-independent measure. 
 * 
 * <p>Implementations must declare whether they are immutable or not.
 * 
 * @author andreww1011
 */
public interface Expression {
    
    /**
     * Returns the dimension of the measure represented by this expression.
     */
    Dimension getDimension();
    
    /**
     * Returns an expression representing the addition of this expression
     * and the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression add(int value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the addition of this expression
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression add(String value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the addition of this expression
     * and the measure represented by the specified scalar and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression add(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this expression
     * and the measure represented by the specified field and unit.
     * @param <T> the type of the field.
     * @param value a field.
     * @param unit the unit of the specified field.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    <T extends Field<T>> Measure<T> add(T value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this expression
     * and the specified measure.
     * @param <T> the type of the field.
     * @param measure a measure.
     * @throws IncommensurableDimensionException if the dimension of the specified measure
     *          is not commensurable with the measure represented by this expression.
     */
    <T extends Field<T>> Measure<T> add(Measure<T> measure) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the addition of this expression
     * and the specified expression.
     * @param expression an expression.
     * @throws IncommensurableDimensionException if the dimension of the specified expression
     *          is not commensurable with this expression.
     */
    Expression add(Expression expression) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the subtraction of this expression
     * and the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression subtract(int value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the subtraction of this expression
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression subtract(String value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the subtraction of this expression
     * and the measure represented by the specified scalar and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    Expression subtract(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this expression
     * and the measure represented by the specified field and unit.
     * @param <T> the type of the field.
     * @param value a field.
     * @param unit the unit of the specified field.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with the measure represented by this expression.
     */
    <T extends Field<T>> Measure<T> subtract(T value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this expression
     * and the specified measure.
     * @param <T> the type of the field.
     * @param measure a measure.
     * @throws IncommensurableDimensionException if the dimension of the specified measure
     *          is not commensurable with the measure represented by this expression.
     */
    <T extends Field<T>> Measure<T> subtract(Measure<T> measure) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the subtraction of this expression
     * and the specified expression.
     * @param expression an expression.
     * @throws IncommensurableDimensionException if the dimension of the specified expression
     *          is not commensurable with this expression.
     */
    Expression subtract(Expression expression) throws IncommensurableDimensionException;
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the specified integer number.
     * @param scalar an integer number.
     */
    Expression multiply(int scalar);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the number represented by the specified string.
     * @param scalar a string representing a number.
     */
    Expression multiply(String scalar);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the specified scalar.
     * @param scalar a scalar.
     */
    Expression multiply(Scalar scalar);
    
    /**
     * Returns a measure representing the multiplication of this expression
     * and the specified field.
     * @param <T> the type of the field.
     * @param scalar a field.
     */
    <T extends Field<T>> Measure<T> multiply(T scalar);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the measure represented by the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     */
    Expression multiply(int value, Unit unit);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     */
    Expression multiply(String value, Unit unit);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the measure represented by the specified scalar and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     */
    Expression multiply(Scalar value, Unit unit);
    
    /**
     * Returns a measure representing the multiplication of this expression
     * and the measure represented by the specified field and unit.
     * @param <T> the type of the field.
     * @param value a field.
     * @param unit the unit of the specified field.
     */
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    /**
     * Returns a measure representing the multiplication of this expression
     * and the specified measure.
     * @param <T> the type of the field.
     * @param measure a measure.
     */
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    /**
     * Returns an expression representing the multiplication of this expression
     * and the specified expression.
     * @param expression an expression.
     */
    Expression multiply(Expression expression);
    
    /**
     * Returns an expression representing the division of this expression
     * and the specified integer number.
     * @param scalar an integer number.
     * @throws ArithmeticException if the specified integer is 0.
     */
    Expression divide(int scalar) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the number represented by the specified string.
     * @param scalar a string representing a number.
     * @throws ArithmeticException if the number represented by the specified string is 0.
     */
    Expression divide(String scalar) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the specified scalar.
     * @param scalar a scalar.
     * @throws ArithmeticException if the specified scalar is 0.
     */
    Expression divide(Scalar scalar) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this expression
     * and the specified field.
     * @param <T> the type of the field.
     * @param scalar a field.
     * @throws ArithmeticException if the specified field is 0.
     */
    <T extends Field<T>> Measure<T> divide(T scalar) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the measure represented by the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws ArithmeticException if the specified integer is 0.
     */
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws ArithmeticException if the number represented by the specified string is 0.
     */
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws ArithmeticException if the specified scalar is 0.
     */
    Expression divide(Scalar value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the measure represented by the specified scalar and unit.
     * @param <T> the type of the field.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws ArithmeticException if the specified field is 0.
     */
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this expression
     * and the specified measure.
     * @param <T> the type of the field.
     * @param measure a measure.
     * @throws ArithmeticException if the value of the specified measure is 0.
     */
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    /**
     * Returns an expression representing the division of this expression
     * and the specified expression.
     * @param expression an expression.
     * @throws ArithmeticException if the value of the specified expression is 0.
     */
    Expression divide(Expression expression) throws ArithmeticException;
    
    /**
     * Returns an expression representing the exponentiation of this expression and the
     * specified exponent.
     * @param exponent an exponent.
     * @throws ArithmeticException if the result of the power operation is undefined.
     */
    Expression power(Exponent exponent) throws ArithmeticException;
    
    /**
     * Particularizes this expression to a measure of a field of type T using the specified factory.
     * @param <T> type of the field to which this expression is particularized into a measure.
     * @param factory factory of the field type to which this expression is particularized into a measure.
     */
    <T extends Field<T>> Measure<T> using(T.Factory<T> factory);
}
