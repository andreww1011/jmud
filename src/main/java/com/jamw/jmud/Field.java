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
 * A field represents a set of numbers together with the binary operations <i>addition</i>
 * and <i>multiplication</i> that satisfy the <a href="https://en.wikipedia.org/wiki/Field_(mathematics)#Classic_definition">field axioms</a>.
 * The set must contain the elements {@link Factory#zero() zero} and {@link Factory#one one},
 * the additive and multiplicative identities, respectively.
 * 
 * <p>For the purposes of this library, the set of operations on a field must also contain 
 * <i>power</i> (and its inverse <i>logarithm</i>).
 * 
 * <p>Implementations of this interface encapsulate the numerical methods and
 * precision used in the calculation of {@link Measure measurements}.
 * 
 * <p>Implementations must declare whether they are immutable or not.
 * 
 * @param <F> this field type (recursive type definition).
 * 
 * @see com.jamw.jmud.fields
 * 
 * @author andreww1011
 */
public interface Field<F extends Field<F>> extends Comparable<F> {
    
    /**
     * A factory interface for creating fields.
     * 
     * @param <T> the type of field this factory creates.
     */
    interface Factory<T extends Field<T>> {
        /**
         * Returns the additive identity <i>zero (0)</i> of the field T.
         */
        T zero();
        
        /**
         * Returns the multiplicative identity <i>one (1)</i> of the field T.
         */
        T one();
        
        /**
         * Returns the field T representation of the specified integer.  
         * Implementations must document the semantics of the conversion.
         */
        T of(int value);
        
        /**
         * Returns the field T representation of the specified String.  
         * Implementations must document the semantics of the conversion.
         * @throws NumberFormatException if the String cannot be parsed into the field T.
         */
        T of(String value) throws NumberFormatException;
    }
        
    /**
     * Returns the factory for this field.
     */
    F.Factory<F> getFactory();
    
    /**
     * Returns the negative or additive inverse of this field, <i>-a</i>.
     * <p><i>a + (-a) = {@linkplain Factory#zero() 0}</i>.
     */
    F negate();
    
    /**
     * Returns the multiplicative inverse of this field, <i>1/a</i>.
     * <p><i>a • (1/a) = {@linkplain Factory#one() 1}</i>.
     * @throws ArithmeticException if this field is equal to {@link Factory#zero() 0}.
     */
    F reciprocal() throws ArithmeticException;
    
    /**
     * Returns the result of the addition operation on this and the specified field.
     * @param b another field.
     * @return c = a + b.
     */
    F add(F b);
    
    /**
     * Returns the result of the addition operation on this and the
     * additive inverse of the specified field.
     * @param b another field.
     * @return c = a + (-b).
     */
    default F subtract(F b) { return this.add(b.negate()); }
    
    /**
     * Returns the result of the multiplication operation on this and the specified field.
     * @param b another field.
     * @return c = a • b.
     */
    F multiply(F b);
    
    /**
     * Returns the result of the multiplication operation on this and the
     * multiplicative inverse of the specified field.
     * @param b another field.
     * @return c = a • (1/b).
     * @throws ArithmeticException if b is equal to {@link Factory#zero() 0}.
     */
    default F divide(F b) throws ArithmeticException { return this.multiply(b.reciprocal()); }

    /**
     * Returns the result of the power operation on this field, where the 
     * specified field is the exponential term.
     * @param exponent another field.
     * @return c = a^exponent.
     * @throws ArithmeticException if the power operation is undefined for the specified exponent.
     */
    F power(F exponent) throws ArithmeticException;
    
    /**
     * Returns the result of the logarithm operation on this field, where the
     * specified field is the logarithmic base.
     * @param base another field.
     * @return c = log_base(a).
     * @throws ArithmeticException if the logarithm operation is undefined for the specified base.
     */
    F logarithm(F base) throws ArithmeticException;

    /**
     * Returns the result of the addition operation on this and the specified scalar
     * particularized to this field type.
     * @param b a scalar.
     * @return c = a + b.
     */
    public default F add(Scalar b) {
        return add(b.using(getFactory()));
    }
    
    /**
     * Returns the result of the addition operation on this and the
     * additive inverse of the specified scalar particularized to this field type.
     * @param b a scalar.
     * @return c = a + (-b).
     */
    public default F subtract(Scalar b) {
        return subtract(b.using(getFactory()));
    }
    
    /**
     * Returns the result of the multiplication operation on this and the specified scalar
     * particularized to this field type.
     * @param b a scalar.
     * @return c = a • b.
     */
    public default F multiply(Scalar b) {
        return multiply(b.using(getFactory()));
    }
    
    /**
     * Returns the result of the multiplication operation on this and the
     * multiplicative inverse of the specified scalar particularized to this field type.
     * @param b a scalar.
     * @return c = a • (1/b).
     * @throws ArithmeticException if b is equal to {@link Factory#zero() 0}.
     */
    public default F divide(Scalar b) {
        return divide(b.using(getFactory()));
    }
    
    /**
     * Returns the result of the power operation on this field, where the the exponential term
     * is the specified scalar particularized to this field type.
     * @param exponent a scalar.
     * @return c = a^exponent.
     * @throws ArithmeticException if the power operation is undefined for the specified exponent.
     */
    public default F power(Scalar exponent) {
        return power(exponent.using(getFactory()));
    }
    
    /**
     * Returns the result of the power operation on this field, where the the exponential term
     * is the value of the specified exponent represented by this field type.
     * @param exponent an exponent.
     * @return c = a^exponent.
     * @throws ArithmeticException if the power operation is undefined for the specified exponent.
     */
    public default F power(Exponent exponent) {
        F e = getFactory().of(exponent.numerator());
        if (exponent.denominator() != 1) //magic number
            e = e.divide(getFactory().of(exponent.denominator()));
        return power(e);
    }
    
    /**
     * Returns the result of the logarithm operation on this field, where the
     * the logarithmic base is the specified scalar particularized to this field type.
     * @param base a scalar.
     * @return c = log_base(a).
     * @throws ArithmeticException if the logarithm operation is undefined for the specified base.
     */
    public default F logarithm(Scalar base) {
        return logarithm(base.using(getFactory()));
    }
    
    /**
     * Compares the specified object with this field for equality. 
     * Returns true if the specified object is a field of this type and the two 
     * fields represent the same value.
     * @param o object to be compared for equality to this field.
     */
    @Override
    boolean equals(Object o);
}
