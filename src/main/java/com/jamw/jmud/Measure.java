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
 * A measure is a number representing a dimensional value.  It is expressed as
 * a numerical value contained in a field, paired with a reference unit.
 * 
 * <p>Two measures have the same value (via {@link #isEqualTo isEqualTo()}) if the
 * magnitudes of the dimension they represent are equal.  That is, the measures 
 * are first converted to identical units and then the values of their fields are compared
 * for equality.  This class has a natural ordering that is inconsistent with equals.
 * 
 * <p>Implementations must declare whether they are immutable or not.
 * @param <F> the field type.
 * 
 * @author andreww1011
 */
public interface Measure<F extends Field<F>> extends Comparable<Measure<F>>{
    
    /**
     * Returns the field of this measure.
     */
    F getField();
    
    /**
     * Returns the unit of this measure.
     */
    Unit getUnit();
    
    /**
     * Compares the specified object with this measure for equality. 
     * Returns true if all of the following conditions are true:
     * <ol>
     * <li>the specified object is a measure.</li>
     * <li>the type of field of the specified measure is the same as this measure.</li>
     * <li>the unit of the specified measure is the same as this measure.</li>
     * <li>the value of the field of the specified measure is equal to this measure's field.</li>
     * </ol>
     * Note: This class has a natural ordering that is inconsistent with equals.
     * @param o object to be compared for equality to this field.
     */
    @Override
    boolean equals(Object o);
    
    /**
     * Compares this measure with the specified measure for order. Returns a 
     * negative integer, zero, or a positive integer as this measure is less than, 
     * equal to, or greater than the specified measure.
     * 
     * <p>A measure is greater than another measure if the magnitude of the
     * dimension it represents is greater.  That is, the measures are first converted to identical
     * units and then the values of their fields are compared.
     * 
     * <p>Note: This class has a natural ordering that is inconsistent with equals.
     * 
     * @param o another measure of this measure's field type.
     * 
     * @throws IncommensurableDimensionException if the dimension of the unit of the
     * specified measure is incommensurable with this measure.
     */
    @Override
    public int compareTo(Measure<F> o) throws IncommensurableDimensionException;
    
    /**
     * {@inheritDoc}
     * 
     * <p>Two measures are equal if the magnitudes of the dimension they
     * represent are equal.  That is, the measures are first converted to identical
     * units and then the values of their fields are compared.
     * 
     * <p>Note: This class has a natural ordering that is inconsistent with equals.
     * 
     * @param o another measure of this measure's field type.
     * 
     * @throws IncommensurableDimensionException if the dimension of the unit of the
     * specified measure is incommensurable with this measure.
     */
    @Override
    default boolean isEqualTo(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) == 0;
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>This measure is less than another measure if the (signed) magnitude of the dimension it
     * represents is less than the (signed) magnitude of the dimension the other measure represents.
     * That is, the measures are first converted to identical
     * units and then the values of their fields are compared.
     * 
     * <p>Note: This class has a natural ordering that is inconsistent with equals.
     * 
     * @param o another measure of this measure's field type.
     * 
     * @throws IncommensurableDimensionException if the dimension of the unit of the
     * specified measure is incommensurable with this measure.
     */
    @Override
    default boolean isLessThan(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) < 0;
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>This measure is greater than another measure if the (signed) magnitude of the dimension it
     * represents is greater than the (signed) magnitude of the dimension the other measure represents.
     * That is, the measures are first converted to identical
     * units and then the values of their fields are compared.
     * 
     * <p>Note: This class has a natural ordering that is inconsistent with equals.
     * 
     * @param o another measure of this measure's field type.
     * 
     * @throws IncommensurableDimensionException if the dimension of the unit of the
     * specified measure is incommensurable with this measure.
     */
    @Override
    default boolean isGreaterThan(Measure<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) > 0;
    }
        
    /**
     * Returns the factory for the field of this measure.
     */
    default F.Factory<F> getFactory() {
        return getField().getFactory();
    }
    
    /**
     * Returns a measure representing the addition of this measure
     * and the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> add(int value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this measure
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> add(String value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this measure
     * and the measure represented by the specified scalar particularized
     * to the field of this measure and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> add(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this measure
     * and the measure represented by the specified field and unit.
     * @param value a field.
     * @param unit the unit of the specified field.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> add(F value, Unit unit) throws IncommensurableDimensionException;
        
    /**
     * Returns a measure representing the addition of this measure
     * and the specified measure.
     * @param measure a measure.
     * @throws IncommensurableDimensionException if the dimension of the specified measure
     *          is not commensurable with this measure.
     */
    Measure<F> add(Measure<F> measure) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the addition of this measure
     * and the specified expression particularized to the field of this measure.
     * @param expression an expression.
     * @throws IncommensurableDimensionException if the dimension of the specified expression
     *          is not commensurable with this measure.
     */
    Measure<F> add(Expression expression) throws IncommensurableDimensionException;
      
    /**
     * Returns a measure representing the subtraction of this measure
     * and the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(int value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this measure
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(String value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this measure
     * and the measure represented by the specified scalar particularized
     * to the field of this measure and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this measure
     * and the measure represented by the specified field and unit.
     * @param value a field.
     * @param unit the unit of the specified field.
     * @throws IncommensurableDimensionException if the dimension of the specified unit
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(F value, Unit unit) throws IncommensurableDimensionException;
        
    /**
     * Returns a measure representing the subtraction of this measure
     * and the specified measure.
     * @param measure a measure.
     * @throws IncommensurableDimensionException if the dimension of the specified measure
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(Measure<F> measure) throws IncommensurableDimensionException;
    
    /**
     * Returns a measure representing the subtraction of this measure
     * and the specified expression particularized to the field of this measure.
     * @param expression an expression.
     * @throws IncommensurableDimensionException if the dimension of the specified expression
     *          is not commensurable with this measure.
     */
    Measure<F> subtract(Expression expression) throws IncommensurableDimensionException;
        
    /**
     * Returns a measure representing the multiplication of this measure
     * and the specified integer number.
     * @param scalar an integer number.
     */
    Measure<F> multiply(int scalar);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the number represented by the specified string.
     * @param scalar a string representing a number.
     */
    Measure<F> multiply(String scalar);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the specified scalar particularized to the field of this measure.
     * @param scalar a scalar.
     */
    Measure<F> multiply(Scalar scalar);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the specified field.
     * @param scalar a field.
     */
    Measure<F> multiply(F scalar);
        
    /**
     * Returns a measure representing the multiplication of this measure
     * and the measure represented by the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     */
    Measure<F> multiply(int value, Unit unit);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     */
    Measure<F> multiply(String value, Unit unit);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the measure represented by the specified scalar particularized
     * to the field of this measure and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     */
    Measure<F> multiply(Scalar value, Unit unit);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the measure represented by the specified field and unit.
     * @param value a field.
     * @param unit the unit of the specified field.
     */
    Measure<F> multiply(F value, Unit unit);
        
    /**
     * Returns a measure representing the multiplication of this measure
     * and the specified measure.
     * @param measure a measure.
     */
    Measure<F> multiply(Measure<F> measure);
    
    /**
     * Returns a measure representing the multiplication of this measure
     * and the specified expression particularized
     * to the field of this measure.
     * @param expression an expression.
     */
    Measure<F> multiply(Expression expression);
       
    /**
     * Returns a measure representing the division of this measure
     * and the specified integer number.
     * @param scalar an integer number.
     * @throws ArithmeticException if the specified integer is 0.
     */
    Measure<F> divide(int scalar) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the number represented by the specified string.
     * @param scalar a string representing a number.
     * @throws ArithmeticException if the number represented by the specified string is 0.
     */
    Measure<F> divide(String scalar) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the specified scalar particularized to the field of this measure.
     * @param scalar a scalar.
     * @throws ArithmeticException if the specified scalar is 0.
     */
    Measure<F> divide(Scalar scalar) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the specified scalar particularized to the field of this measure.
     * @param scalar a scalar.
     * @throws ArithmeticException if the specified field is 0.
     */
    Measure<F> divide(F scalar) throws ArithmeticException;
        
    /**
     * Returns a measure representing the division of this measure
     * and the measure represented by the specified integer number and unit.
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @throws ArithmeticException if the specified integer is 0.
     */
    Measure<F> divide(int value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the measure represented by the number represented by the specified string and unit.
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @throws ArithmeticException if the number represented by the specified string is 0.
     */
    Measure<F> divide(String value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the measure represented by the specified scalar particularized
     * to the field of this measure and unit.
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @throws ArithmeticException if the specified scalar is 0.
     */
    Measure<F> divide(Scalar value, Unit unit) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the measure represented by the specified field and unit.
     * @param value a field.
     * @param unit the unit of the specified field.
     * @throws ArithmeticException if the specified field is 0.
     */
    Measure<F> divide(F value, Unit unit) throws ArithmeticException;
        
    /**
     * Returns a measure representing the division of this measure
     * and the specified measure.
     * @param measure a measure.
     * @throws ArithmeticException if the value of the specified measure is 0.
     */
    Measure<F> divide(Measure<F> measure) throws ArithmeticException;
    
    /**
     * Returns a measure representing the division of this measure
     * and the specified expression particularized
     * to the field of this measure.
     * @param expression an expression.
     * @throws ArithmeticException if the value of the specified expression is 0.
     */
    Measure<F> divide(Expression expression) throws ArithmeticException;
    
    /**
     * Returns a measure representing the exponentiation of this measure and the
     * specified exponent.
     * @param exponent an exponent.
     * @throws ArithmeticException if the result of the power operation is undefined.
     */
    Measure<F> power(Exponent exponent) throws ArithmeticException;
    
    /**
     * Returns a measure equivalent to this measure but in the specified units.
     * @param unit the unit to convert to.
     * @throws IncommensurableDimensionException if the dimension of the specified
     * unit is not commensurable with this measure.
     */
    Measure<F> as(Unit unit) throws IncommensurableDimensionException;
}
