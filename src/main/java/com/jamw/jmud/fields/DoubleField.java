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
package com.jamw.jmud.fields;

import com.jamw.jmud.Constants;
import com.jamw.jmud.Exponent;
import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import com.jamw.jmud.Field;
import com.jamw.jmud.IncommensurableDimensionException;
import com.jamw.jmud.Scalar;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import java.util.Objects;

/**
 * A {@link com.jamw.jmud.Field Field} implementation using the java 
 * {@code double} primitive as the underlying numerical implementation.
 * 
 * <p>All instances of this class are immutable and thread-safe.
 * 
 * @author andreww1011
 */
public final class DoubleField implements Field<DoubleField>, Field.Factory<DoubleField> {
    
    /**
     * The value 0.
     */
    public static final DoubleField ZERO = new DoubleField(0);
    
    /**
     * The value 1.
     */
    public static final DoubleField ONE = new DoubleField(1);
    
    /**
     * The value 10.
     */
    public static final DoubleField TEN = new DoubleField(10);

    /**
     * The value of the base of the natural logarithm, <i>e</i>.
     */
    private static final DoubleField E = Constants.euler.using(factory());
    
    /**
     * Returns a factory for the <code>DoubleField</code> type.
     */
    public static final Field.Factory<DoubleField> factory() {
        return ZERO;
    }
    
    /**
     * Returns a {@code DoubleField} of the specified value.
     */
    public static final DoubleField of(double value) {
        return new DoubleField(value);
    }
    
    private final double value;
    
    private DoubleField(double value) {
        this.value = value;
    }
    
    /**
     * Returns the {@code double} value underlying this {@code DoubleField}.
     * @return 
     */
    public double value() {
        return value;
    }
    
    @Override
    public final Field.Factory<DoubleField> getFactory() {
        return factory();
    }
    
    @Override
    public final DoubleField zero() {
        return ZERO;
    }
    
    @Override
    public final DoubleField one() {
        return ONE;
    }
    
    @Override
    public final DoubleField of(int value) {
        return new DoubleField(value);
    }
    
    @Override
    public final DoubleField of(String value) {
        return new DoubleField(Double.parseDouble(value));
    }
    
    @Override
    public final DoubleField add(DoubleField f) {
        return new DoubleField(this.value + f.value());
    }
    
    @Override
    public final DoubleField subtract(DoubleField f) {
        return new DoubleField(this.value - f.value());
    }
    
    @Override
    public final DoubleField multiply(DoubleField f) {
        return new DoubleField(this.value * f.value());
    }
    
    @Override
    public final DoubleField divide(DoubleField f) {
        if (f.value() == 0)
            throw new ArithmeticException("Division by zero");
        return new DoubleField(this.value / f.value());
    }
    
    @Override
    public final DoubleField negate() {
        return new DoubleField(-value);
    }
    
    @Override
    public final DoubleField reciprocal() {
        if (value == 0)
            throw new ArithmeticException("Division by zero");
        return new DoubleField(1/value);
    }
    
    @Override
    public final DoubleField power(DoubleField exponent) throws ArithmeticException {
        return new DoubleField(checkNan(Math.pow(value,exponent.value())));
    }
    
    private static double checkNan(double d) {
        if (Double.compare(d,Double.NaN) == 0 || Double.compare(d,Double.NEGATIVE_INFINITY) == 0|| Double.compare(d,Double.POSITIVE_INFINITY) == 0)
            throw new ArithmeticException();
        return d;
    }
    
    @Override
    public final DoubleField logarithm(DoubleField base) throws ArithmeticException {
        if (base.isEqualTo(E))
            return new DoubleField(checkNan(Math.log(value)));
        double a = checkNan(Math.log10(value));
        if (base.isEqualTo(TEN))
            return new DoubleField(a);
        double b = checkNan(Math.log10(base.value()));
        return new DoubleField(a/b);
        
    }
    
    @Override
    public final int compareTo(DoubleField o) {
        return Double.compare(value,o.value());
    }
    
    @Override 
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DoubleField))
            return false;
        return compareTo((DoubleField)o) == 0;
    }

    @Override
    public final int hashCode() {
        return Double.hashCode(value);
    }
    
    @Override
    public final String toString() {
        return Double.toString(value);
    }
    
    /**
     * A {@link com.jamw.jmud.Measure Measure} implementation using a 
     * {@link DoubleField} as the underlying field implementation.
     * 
     * <p>All instances of this class are immutable and thread-safe.
     */
    public static final class Measure implements com.jamw.jmud.Measure<DoubleField> {

        /**
         * Returns a {@code DoubleField} measure of the specified double and unit.
         * @param value a double number.
         * @param unit the unit of the specified number.
         */
        public static final DoubleField.Measure of(double value,Unit unit) {
            return of(DoubleField.of(value),unit);
        }
        
        /**
         * Returns a {@code DoubleField} measure of the specified {@code DoubleField} and unit.
         * @param value a {@code DoubleField} number.
         * @param unit the unit of the specified number.
         */
        public static final DoubleField.Measure of(DoubleField value,Unit unit) {
            return of(Expressions.take(value,unit));
        }
        
        /**
         * Wraps the specified measure into a {@code DoubleField.Measure}.
         * @param measure a measure.
         */
        public static final DoubleField.Measure of(com.jamw.jmud.Measure<DoubleField> measure) {
            return new Measure(measure);
        }
        
        /**
         * Returns the absolute value of the measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#abs(double)
         */
        public static final DoubleField.Measure abs(Expression expression) {
            return abs(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the absolute value of the specified measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#abs(double)
         */
        public static final DoubleField.Measure abs(com.jamw.jmud.Measure<DoubleField> measure) {
            return of(Math.abs(measure.getField().value()),measure.getUnit());
        }
        
        /**
         * Returns the ceiling of the measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#ceil(double)
         */
        public static final DoubleField.Measure ceil(Expression expression) {
            return ceil(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the ceiling of the specified measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#ceil(double)
         */
        public static final DoubleField.Measure ceil(com.jamw.jmud.Measure<DoubleField> measure) {
            return of(Math.ceil(measure.getField().value()),measure.getUnit());
        }
        
        /**
         * Returns the floor of the measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#floor(double)
         */
        public static final DoubleField.Measure floor(Expression expression) {
            return floor(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the floor of the specified measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#floor(double)
         */
        public static final DoubleField.Measure floor(com.jamw.jmud.Measure<DoubleField> measure) {
            return of(Math.floor(measure.getField().value()),measure.getUnit());
        }
        
        /**
         * Returns the sign of the measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#signum(double)
         */
        public static final DoubleField signum(Expression expression) {
            return signum(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the sign of the specified measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#signum(double)
         */
        public static final DoubleField signum(com.jamw.jmud.Measure<DoubleField> measure) {
            return DoubleField.of(Math.signum(measure.getField().value()));
        }
        
        /**
         * Returns the cosine of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#cos(double)
         */
        public static final DoubleField.Measure cos(Expression expression) {
            return cos(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the cosine of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#cos(double)
         */
        public static final DoubleField.Measure cos(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.RADIAN);
            return of(Math.cos(m.getField().value()),Units.UNITLESS);
        }
        
        /**
         * Returns the sine of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#sin(double)
         */
        public static final DoubleField.Measure sin(Expression expression) {
            return sin(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the sine of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#sin(double)
         */
        public static final DoubleField.Measure sin(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.RADIAN);
            return of(Math.sin(m.getField().value()),Units.UNITLESS);
        }
        
        /**
         * Returns the tangent of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#tan(double)
         */
        public static final DoubleField.Measure tan(Expression expression) {
            return tan(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the tangent of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#tan(double)
         */
        public static final DoubleField.Measure tan(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.RADIAN);
            return of(Math.tan(m.getField().value()),Units.UNITLESS);
        }
        
        /**
         * Returns the arc cosine of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#acos(double)
         */
        public static final DoubleField.Measure acos(Expression expression) {
            return acos(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the arc cosine of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#acos(double)
         */
        public static final DoubleField.Measure acos(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.UNITLESS);
            return of(Math.acos(m.getField().value()),Units.RADIAN);
        }
        
        /**
         * Returns the arc sine of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#asin(double)
         */
        public static final DoubleField.Measure asin(Expression expression) {
            return asin(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the arc sine of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#asin(double)
         */
        public static final DoubleField.Measure asin(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.UNITLESS);
            return of(Math.asin(m.getField().value()),Units.RADIAN);
        }
        
        /**
         * Returns the arc tangent of the (dimensionless) measure represented by
         * the specified expression particularized to a {@code DoubleField.Measure}.
         *
         * @param expression an expression.
         * 
         * @see Math#atan(double)
         */
        public static final DoubleField.Measure atan(Expression expression) {
            return atan(expression.using(DoubleField.factory()));
        }
        
        /**
         * Returns the arc tangent of the specified (dimensionless) measure.
         * 
         * @param measure a measure.
         * 
         * @see Math#atan(double)
         */
        public static final DoubleField.Measure atan(com.jamw.jmud.Measure<DoubleField> measure) {
            com.jamw.jmud.Measure<DoubleField> m = measure.as(Units.UNITLESS);
            return of(Math.atan(m.getField().value()),Units.RADIAN);
        }
        
        /**
         * Returns the arc tangent of the (dimensionless) measure represented by
         * the specified expressions particularized to a {@code DoubleField.Measure}.
         *
         * @param x an expression for the ordinate coordinate.
         * @param y an expression for the abscissa coordinate.
         * 
         * @see Math#atan2(double,double)
         */
        public static final DoubleField.Measure atan2(Expression x, Expression y) {
            return atan2(x.using(DoubleField.factory()),y.using(DoubleField.factory()));
        }
        
        /**
         * Returns the arc tangent of the specified (dimensionless) measures.
         *
         * @param x a measure for the ordinate coordinate.
         * @param y a measure for the abscissa coordinate.
         * 
         * @see Math#atan2(double,double)
         */
        public static final DoubleField.Measure atan2(com.jamw.jmud.Measure<DoubleField> x, com.jamw.jmud.Measure<DoubleField> y) {
            com.jamw.jmud.Measure<DoubleField> y2 = y.as(x.getUnit());
            return of(Math.atan2(x.getField().value(),y2.getField().value()),Units.RADIAN);
        }
                
        private final com.jamw.jmud.Measure<DoubleField> measure;
        
        private Measure(com.jamw.jmud.Measure<DoubleField> measure) {
            this.measure = measure;
        }
        
        @Override
        public DoubleField getField() {
            return measure.getField();
        }

        @Override
        public Unit getUnit() {
            return measure.getUnit();
            
        }

        @Override
        public int compareTo(com.jamw.jmud.Measure<DoubleField> o) throws IncommensurableDimensionException {
            return measure.compareTo(o);
        }

        @Override
        public DoubleField.Measure add(int value,Unit unit)
                throws IncommensurableDimensionException {
            return of(measure.add(value,unit));
        }

        @Override
        public DoubleField.Measure add(String value,Unit unit)
                throws IncommensurableDimensionException {
            return of(measure.add(value,unit));
        }

        @Override
        public DoubleField.Measure add(Scalar value,Unit unit)
                throws IncommensurableDimensionException {
            return of(measure.add(value,unit));
        }

        @Override
        public DoubleField.Measure add(DoubleField value, Unit unit) 
                throws IncommensurableDimensionException {
            return of(measure.add(value,unit));
        }

        @Override
        public DoubleField.Measure add(com.jamw.jmud.Measure<DoubleField> measure)
                throws IncommensurableDimensionException {
            return of(this.measure.add(measure));
        }

        @Override
        public DoubleField.Measure add(Expression expression)
                throws IncommensurableDimensionException {
            return of(measure.add(expression));
        }

        @Override
        public DoubleField.Measure subtract(int value,Unit unit)
                throws IncommensurableDimensionException {
            return of(measure.subtract(value,unit));
        }

        @Override
        public DoubleField.Measure subtract(String value, Unit unit) 
                throws IncommensurableDimensionException {
            return of(measure.subtract(value,unit));
        }

        @Override
        public DoubleField.Measure subtract(Scalar value, Unit unit) 
                throws IncommensurableDimensionException {
            return of(measure.subtract(value,unit));
        }

        @Override
        public DoubleField.Measure subtract(DoubleField value, Unit unit) 
                throws IncommensurableDimensionException {
            return of(measure.subtract(value,unit));
        }

        @Override
        public DoubleField.Measure subtract(com.jamw.jmud.Measure<DoubleField> measure)
                throws IncommensurableDimensionException {
            return of(this.measure.subtract(measure));
        }

        @Override
        public DoubleField.Measure subtract(Expression expression)
                throws IncommensurableDimensionException {
            return of(measure.subtract(expression));
        }

        @Override
        public DoubleField.Measure multiply(int scalar) {
            return of(measure.multiply(scalar));
        }

        @Override
        public DoubleField.Measure multiply(String scalar) {
            return of(measure.multiply(scalar));
        }

        @Override
        public DoubleField.Measure multiply(Scalar scalar) {
            return of(measure.multiply(scalar));
        }

        @Override
        public DoubleField.Measure multiply(DoubleField scalar) {
            return of(measure.multiply(scalar));
        }

        @Override
        public DoubleField.Measure multiply(int value,Unit unit) {
            return of(measure.multiply(value,unit));
        }

        @Override
        public DoubleField.Measure multiply(String value, Unit unit) {
            return of(measure.multiply(value,unit));
        }

        @Override
        public DoubleField.Measure multiply(Scalar value, Unit unit) {
            return of(measure.multiply(value,unit));
        }

        @Override
        public DoubleField.Measure multiply(DoubleField value, Unit unit) {
            return of(measure.multiply(value,unit));
        }

        @Override
        public DoubleField.Measure multiply(com.jamw.jmud.Measure<DoubleField> measure) {
            return of(this.measure.multiply(measure));
        }

        @Override
        public DoubleField.Measure multiply(Expression expression) {
            return of(measure.multiply(expression));
        }

        @Override
        public DoubleField.Measure divide(int scalar) 
                throws ArithmeticException {
            return of(measure.divide(scalar));
        }

        @Override
        public DoubleField.Measure divide(String scalar) 
                throws ArithmeticException {
            return of(measure.divide(scalar));
        }

        @Override
        public DoubleField.Measure divide(Scalar scalar) 
                throws ArithmeticException {
            return of(measure.divide(scalar));
        }

        @Override
        public DoubleField.Measure divide(DoubleField scalar)
                throws ArithmeticException {
            return of(measure.divide(scalar));
        }

        @Override
        public DoubleField.Measure divide(int value,Unit unit)
                throws ArithmeticException {
            return of(measure.divide(value,unit));
        }

        @Override
        public DoubleField.Measure divide(String value,Unit unit)
                throws ArithmeticException {
            return of(measure.divide(value,unit));
        }

        @Override
        public DoubleField.Measure divide(Scalar value,Unit unit)
                throws ArithmeticException {
            return of(measure.divide(value,unit));
        }

        @Override
        public DoubleField.Measure divide(DoubleField value, Unit unit)
                throws ArithmeticException {
            return of(measure.divide(value,unit));
        }

        @Override
        public DoubleField.Measure divide(com.jamw.jmud.Measure<DoubleField> measure)
                throws ArithmeticException {
            return of(this.measure.divide(measure));
        }

        @Override
        public DoubleField.Measure divide(Expression expression)
                throws ArithmeticException {
            return of(measure.divide(expression));
        }

        @Override
        public DoubleField.Measure power(Exponent exponent)
                throws ArithmeticException {
            return of(measure.power(exponent));   
        }

        @Override
        public DoubleField.Measure as(Unit unit)
                throws IncommensurableDimensionException {
            return of(measure.as(unit));
        }
        
        @Override
        public String toString() {
            return measure.toString();
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof DoubleField.Measure))
                return false;
            DoubleField.Measure om = (DoubleField.Measure)o;
            return this.getUnit().equals(om.getUnit())
                    && this.getField().isEqualTo(om.getField());
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + Objects.hashCode(measure.getField().value());
            hash = 37 * hash + Objects.hashCode(measure.getUnit());
            return hash;
        }
        
        /**
         * Returns the absolute value of this measure.
         * 
         * @see Math#abs(double)
         */
        public DoubleField.Measure abs() {
            return abs(this);
        }
        
        /**
         * Returns the ceiling of this measure.
         * 
         * @see Math#ceil(double)
         */
        public DoubleField.Measure ceil() {
            return ceil(this);
        }
        
        /**
         * Returns the floor of this measure.
         * 
         * @see Math#floor(double)
         */
        public DoubleField.Measure floor() {
            return floor(this);
        }
    }
}
