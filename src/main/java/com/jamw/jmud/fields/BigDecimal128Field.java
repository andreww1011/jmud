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

import com.jamw.jmud.Exponent;
import com.jamw.jmud.Exponents;
import com.jamw.jmud.Field;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author andreww1011
 */
public final class BigDecimal128Field implements Field<BigDecimal128Field>, Field.Factory<BigDecimal128Field> {

    private static final MathContext MATH_CONTEXT = MathContext.DECIMAL128;
    
    public static final BigDecimal128Field ZERO = new BigDecimal128Field(BigDecimal.ZERO);
    public static final BigDecimal128Field ONE = new BigDecimal128Field(BigDecimal.ONE);
    
    public static final Field.Factory<BigDecimal128Field> factory() {
        return ZERO;
    }
    
    public static final BigDecimal128Field of(BigDecimal value) {
        return new BigDecimal128Field(value);
    }
    
    private final BigDecimal value;
    
    private BigDecimal128Field(BigDecimal value) {
        this.value = value;
    }
    
    @Override
    public final Field.Factory<BigDecimal128Field> getFactory() {
        return factory();
    }
    
    @Override
    public final BigDecimal128Field of(int value) {
        return new BigDecimal128Field(new BigDecimal(value));
    }

    @Override
    public final BigDecimal128Field of(String value) {
        return new BigDecimal128Field(new BigDecimal(value));
    }
    
    public final BigDecimal value() {
        return value;
    }
    
    @Override
    public final BigDecimal128Field negate() {
        return new BigDecimal128Field(value.negate());
    }

    @Override
    public final BigDecimal128Field reciprocal() throws ArithmeticException {
        return new BigDecimal128Field(BigDecimal.ONE.divide(value,MATH_CONTEXT));
    }

    @Override
    public final BigDecimal128Field add(BigDecimal128Field f) {
        if (this.isEqualTo(ZERO))
            return f;
        if (f.isEqualTo(ZERO))
            return this;
        return new BigDecimal128Field(this.value.add(f.value()));
    }

    @Override
    public final BigDecimal128Field multiply(BigDecimal128Field f) {
        if (this.isEqualTo(ZERO) || f.isEqualTo(ZERO))
            return ZERO;
        if (this.isEqualTo(ONE))
            return f;
        if (f.isEqualTo(ONE))
            return this;
        return new BigDecimal128Field(this.value.multiply(f.value()));
    }
    
    @Override
    public final BigDecimal128Field divide(BigDecimal128Field f) {
        if (this.isEqualTo(ZERO))
            return ZERO;
        if (f.isEqualTo(ONE))
            return this;
        return new BigDecimal128Field(this.value.divide(f.value(),MATH_CONTEXT));
    }
    
    @Override
    public final BigDecimal128Field power(BigDecimal128Field exponent) {
        if (exponent.isEqualTo(ZERO))
            return ONE;
        if (exponent.isEqualTo(ONE))
            return this;
        if (exponent.isLessThan(ZERO))
            throw new UnsupportedOperationException();
        if (isInteger(exponent))  {
            int i = exponent.value().intValue();
            return new BigDecimal128Field(value.pow(i));
        } 
        if (!hasSquareRoot(exponent))
            throw new UnsupportedOperationException();
        int i = exponent.value().intValue();
        return new BigDecimal128Field(value.pow(i).multiply(value.sqrt(MATH_CONTEXT)));
    }
    
    private static boolean isInteger(BigDecimal128Field d) {
        int i;
        try {
            i = d.value().intValueExact();
        } catch (ArithmeticException e) {
            return false;
        }
        return i != 0;
    }
    
    private static BigDecimal ONE_HALF = new BigDecimal("0.5");
    
    private static boolean hasSquareRoot(BigDecimal128Field d) {
        return d.value().remainder(ONE_HALF).compareTo(BigDecimal.ZERO) == 0;
    }
    
    @Override
    public final BigDecimal128Field power(Exponent exponent) {
        if (exponent.isEqualTo(Exponents.ZERO))
            return ONE;
        if (exponent.isEqualTo(Exponents.ONE))
            return this;
        if (exponent.isLessThan(Exponents.ZERO))
            throw new UnsupportedOperationException();
        if (exponent.denominator() == 1) 
            return new BigDecimal128Field(value.pow((exponent.numerator())));
        if (exponent.denominator() % 2 != 0) 
            throw new UnsupportedOperationException();
        int n = exponent.denominator() / 2;
        BigDecimal v = value;
        for (int i=0; i < n; i++)
            v = v.sqrt(MATH_CONTEXT);
        if (exponent.numerator() != 1)
            v = v.pow(exponent.numerator());
        return new BigDecimal128Field(v);
    }
    
    @Override
    public BigDecimal128Field logarithm(BigDecimal128Field base) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final int compareTo(BigDecimal128Field o) {
        return this.value.compareTo(o.value());
    }
    
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BigDecimal128Field))
            return false;
        BigDecimal128Field b = (BigDecimal128Field)o;
        return this.isEqualTo(b);
    }
    
    @Override
    public final int hashCode() {
        return value.hashCode();
    }
    
    @Override
    public final String toString() {
        return value.toPlainString();
    }
}
