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
import com.jamw.jmud.Field;

/**
 *
 * @author andreww1011
 */
public final class DoubleField implements Field<DoubleField>, Field.Factory<DoubleField> {
    
    public static final DoubleField ZERO = new DoubleField(0);
    public static final DoubleField ONE = new DoubleField(1);
    public static final DoubleField TEN = new DoubleField(10);

    private static final DoubleField E = Constants.euler.using(factory());
    
    
    public static final Field.Factory<DoubleField> factory() {
        return ZERO;
    }
    
    public static final DoubleField of(double value) {
        return new DoubleField(value);
    }
    
    private final double value;
    
    private DoubleField(double value) {
        this.value = value;
    }
    
    public double value() {
        return value;
    }
    
    @Override
    public final Field.Factory<DoubleField> getFactory() {
        return factory();
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
//    
//    public static final class Measure implements com.jamw.jmud.Measure<DoubleField> {
//
//        public static final Measure of(int value) {
//            return of(value,Units.UNITLESS);
//        }
//        
//        public static final Measure of(int value, Unit unit) {
//            return of(DoubleField.factory().of(value),unit);
//        }
//        
//        public static final Measure of(double value) {
//            return of(value,Units.UNITLESS);
//        }
//        
//        public static final Measure of(double value, Unit unit) {
//            return of(DoubleField.of(value),unit);
//        }
//        
//        public static final Measure of(String value) {
//            return of(value,Units.UNITLESS);
//        }
//        
//        public static final Measure of(String value, Unit unit) {
//            return of(DoubleField.factory().of(value),unit);
//        }
//        
//        public static final Measure of(DoubleField value) {
//            return of(value,Units.UNITLESS);
//        }
//        
//        public static final Measure of(DoubleField value, Unit unit) {
//            return of(Measures.take(value,unit));
//        }
//        
//        public static final Measure of(com.jamw.jmud.Measure<DoubleField> measure) {
//            return new Measure(measure);
//        }
//        
//        private final com.jamw.jmud.Measure<DoubleField> measure;
//        
//        private Measure(com.jamw.jmud.Measure<DoubleField> measure) {
//            this.measure = measure;
//        }
//        
//        @Override
//        public DoubleField getField() {
//            return measure.getField();
//        }
//
//        @Override
//        public Unit getUnit() {
//            return measure.getUnit();
//        }
//
//        @Override
//        public int compareTo(com.jamw.jmud.Measure<DoubleField> o) throws IncommensurableDimensionException {
//            return measure.compareTo(o);
//        }
//
//        @Override
//        public Measure add(int value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.add(value,unit));
//        }
//
//        @Override
//        public Measure add(String value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.add(value,unit));
//        }
//
//        @Override
//        public Measure add(Scalar value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.add(value,unit));
//        }
//
//        @Override
//        public Measure add(DoubleField value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.add(value,unit));
//        }
//
//        @Override
//        public Measure add(com.jamw.jmud.Measure<DoubleField> measure) throws IncommensurableDimensionException {
//            return new Measure(this.measure.add(measure));
//        }
//
//        @Override
//        public Measure add(Expression expression) throws IncommensurableDimensionException {
//            return new Measure(measure.add(expression));
//        }
//
//        @Override
//        public Measure subtract(int value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.subtract(value,unit));
//        }
//
//        @Override
//        public Measure subtract(String value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.subtract(value,unit));
//        }
//
//        @Override
//        public Measure subtract(Scalar value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.subtract(value,unit));
//        }
//
//        @Override
//        public Measure subtract(DoubleField value,Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.subtract(value,unit));
//        }
//
//        @Override
//        public Measure subtract(com.jamw.jmud.Measure<DoubleField> measure) throws IncommensurableDimensionException {
//            return new Measure(this.measure.subtract(measure));
//        }
//
//        @Override
//        public Measure subtract(Expression expression) throws IncommensurableDimensionException {
//            return new Measure(measure.subtract(expression));
//        }
//
//        @Override
//        public Measure multiply(int scalar) {
//            return new Measure(measure.multiply(scalar));
//        }
//
//        @Override
//        public Measure multiply(String scalar) {
//            return new Measure(measure.multiply(scalar));
//        }
//
//        @Override
//        public Measure multiply(Scalar scalar) {
//            return new Measure(measure.multiply(scalar));
//        }
//
//        @Override
//        public Measure multiply(DoubleField scalar) {
//            return new Measure(measure.multiply(scalar));
//        }
//
//        @Override
//        public Measure multiply(int value,Unit unit) {
//            return new Measure(measure.multiply(value,unit));
//        }
//
//        @Override
//        public Measure multiply(String value,Unit unit) {
//            return new Measure(measure.multiply(value,unit));
//        }
//
//        @Override
//        public Measure multiply(Scalar value,Unit unit) {
//            return new Measure(measure.multiply(value,unit));
//        }
//
//        @Override
//        public Measure multiply(DoubleField value,Unit unit) {
//            return new Measure(measure.multiply(value,unit));
//        }
//
//        @Override
//        public Measure multiply(com.jamw.jmud.Measure<DoubleField> measure) {
//            return new Measure(this.measure.multiply(measure));
//        }
//
//        @Override
//        public Measure multiply(Expression expression) {
//            return new Measure(measure.multiply(expression));
//        }
//
//        @Override
//        public Measure divide(int scalar) {
//            return new Measure(measure.divide(scalar));
//        }
//
//        @Override
//        public Measure divide(String scalar) {
//            return new Measure(measure.divide(scalar));
//        }
//
//        @Override
//        public Measure divide(Scalar scalar) {
//            return new Measure(measure.divide(scalar));
//        }
//
//        @Override
//        public Measure divide(DoubleField scalar) {
//            return new Measure(measure.divide(scalar));
//        }
//
//        @Override
//        public Measure divide(int value,Unit unit) {
//            return new Measure(measure.divide(value,unit));
//        }
//
//        @Override
//        public Measure divide(String value,Unit unit) {
//            return new Measure(measure.divide(value,unit));
//        }
//
//        @Override
//        public Measure divide(Scalar value,Unit unit) {
//            return new Measure(measure.divide(value,unit));
//        }
//
//        @Override
//        public Measure divide(DoubleField value,Unit unit) {
//            return new Measure(measure.divide(value,unit));
//        }
//
//        @Override
//        public Measure divide(com.jamw.jmud.Measure<DoubleField> measure) {
//            return new Measure(this.measure.divide(measure));
//        }
//
//        @Override
//        public Measure divide(Expression expression) {
//            return new Measure(measure.divide(expression));
//        }
//
//        @Override
//        public Measure as(Unit unit) throws IncommensurableDimensionException {
//            return new Measure(measure.as(unit));
//        }
//    }
}
