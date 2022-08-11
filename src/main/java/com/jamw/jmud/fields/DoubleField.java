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
    public static final DoubleField PI = factory().of(Constants.pi);
    public static final DoubleField E = factory().of(Constants.euler);
    
    
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
}
