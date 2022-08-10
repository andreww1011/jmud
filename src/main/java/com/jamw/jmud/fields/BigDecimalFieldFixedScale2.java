/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamw.jmud.fields;

import com.jamw.jmud.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Andrew
 */
public final class BigDecimalFieldFixedScale2 implements Field<BigDecimalFieldFixedScale2>, Field.Factory<BigDecimalFieldFixedScale2> {

    private static final int SCALE = 2; //magic number
    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    
    public static final BigDecimalFieldFixedScale2 ZERO = new BigDecimalFieldFixedScale2(BigDecimal.ZERO);
    public static final BigDecimalFieldFixedScale2 ONE = new BigDecimalFieldFixedScale2(BigDecimal.ONE);
    
    public static final Field.Factory<BigDecimalFieldFixedScale2> factory() {
        return ZERO;
    }
    
    public static final BigDecimalFieldFixedScale2 of(BigDecimal value) {
        return new BigDecimalFieldFixedScale2(value);
    }
    
    private final BigDecimal value;
    
    private BigDecimalFieldFixedScale2(BigDecimal value) {
        this(value,false);
    }
    
    private BigDecimalFieldFixedScale2(BigDecimal value, boolean isScaled) {
        if (isScaled) {
            this.value = value;
        } else {
            this.value = value.setScale(SCALE,ROUNDING_MODE);
        }
    }
    
    @Override
    public final Field.Factory<BigDecimalFieldFixedScale2> getFactory() {
        return factory();
    }
    
    @Override
    public final BigDecimalFieldFixedScale2 of(int value) {
        return new BigDecimalFieldFixedScale2(new BigDecimal(value));
    }

    @Override
    public final BigDecimalFieldFixedScale2 of(String value) {
        return new BigDecimalFieldFixedScale2(new BigDecimal(value));
    }
    
    public final BigDecimal value() {
        return value;
    }
    
    @Override
    public final BigDecimalFieldFixedScale2 negate() {
        return new BigDecimalFieldFixedScale2(value.negate(),true);
    }

    @Override
    public final BigDecimalFieldFixedScale2 reciprocal() throws ArithmeticException {
        return new BigDecimalFieldFixedScale2(BigDecimal.ONE.divide(value,SCALE,ROUNDING_MODE),true);
    }

    @Override
    public final BigDecimalFieldFixedScale2 add(BigDecimalFieldFixedScale2 f) {
        if (this.isEqualTo(ZERO))
            return f;
        if (f.isEqualTo(ZERO))
            return this;
        return new BigDecimalFieldFixedScale2(this.value.add(f.value()),true);
    }

    @Override
    public final BigDecimalFieldFixedScale2 multiply(BigDecimalFieldFixedScale2 f) {
        if (this.isEqualTo(ZERO) || f.isEqualTo(ZERO))
            return ZERO;
        if (this.isEqualTo(ONE))
            return f;
        if (f.isEqualTo(ONE))
            return this;
        return new BigDecimalFieldFixedScale2(this.value.multiply(f.value()),false);
    }
    
    @Override
    public final BigDecimalFieldFixedScale2 divide(BigDecimalFieldFixedScale2 f) {
        if (this.isEqualTo(ZERO))
            return ZERO;
        if (f.isEqualTo(ONE))
            return this;
        return new BigDecimalFieldFixedScale2(this.value.divide(f.value(),SCALE,ROUNDING_MODE),true);
    }
    
    @Override
    public final BigDecimalFieldFixedScale2 power(BigDecimalFieldFixedScale2 exponent) {
        if (exponent.isEqualTo(ZERO))
            return ONE;
        if (exponent.isEqualTo(ONE))
            return this;
        if (isInteger(exponent))  {
            int i = exponent.value().intValue();
            return new BigDecimalFieldFixedScale2(value().pow(i));
        } 
        if (exponent.isLessThan(ZERO) || !isSquareRoot(exponent))
            throw new UnsupportedOperationException();
        int i = exponent.value().intValue();
        return new BigDecimalFieldFixedScale2(value.pow(i).multiply(value.sqrt(new MathContext(value.precision(),ROUNDING_MODE))));
    }
    
    private static boolean isInteger(BigDecimalFieldFixedScale2 d) {
        int i;
        try {
            i = d.value().intValueExact();
        } catch (ArithmeticException e) {
            return false;
        }
        return i != 0;
    }
    
    private static BigDecimal ONE_HALF = new BigDecimal("0.5");
    
    private static boolean isSquareRoot(BigDecimalFieldFixedScale2 d) {
        return d.value().remainder(ONE_HALF).compareTo(BigDecimal.ZERO) == 0;
    }
    
    @Override
    public BigDecimalFieldFixedScale2 logarithm(BigDecimalFieldFixedScale2 base) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public final int compareTo(BigDecimalFieldFixedScale2 o) {
        return this.value.compareTo(o.value());
    }
    
    @Override
    public final boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof BigDecimalFieldFixedScale2))
            return false;
        BigDecimalFieldFixedScale2 b = (BigDecimalFieldFixedScale2)o;
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
