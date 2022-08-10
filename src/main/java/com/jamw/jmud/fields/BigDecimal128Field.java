/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamw.jmud.fields;

import com.jamw.jmud.Field;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Andrew
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
        if (isInteger(exponent))  {
            int i = exponent.value().intValue();
            return new BigDecimal128Field(value().pow(i));
        } 
        if (exponent.isLessThan(ZERO) || !isSquareRoot(exponent))
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
    
    private static boolean isSquareRoot(BigDecimal128Field d) {
        return d.value().remainder(ONE_HALF).compareTo(BigDecimal.ZERO) == 0;
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
