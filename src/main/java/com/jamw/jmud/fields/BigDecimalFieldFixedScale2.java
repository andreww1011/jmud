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
import java.math.RoundingMode;

/**
 *
 * @author andreww1011
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
        if (exponent.isLessThan(ZERO))
            throw new UnsupportedOperationException();
        if (isInteger(exponent))  {
            int i = exponent.value().intValue();
            return new BigDecimalFieldFixedScale2(value.pow(i));
        } 
        if (!hasSquareRoot(exponent))
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
    
    private static boolean hasSquareRoot(BigDecimalFieldFixedScale2 d) {
        return d.value().remainder(ONE_HALF).compareTo(BigDecimal.ZERO) == 0;
    }
    
    @Override
    public final BigDecimalFieldFixedScale2 power(Exponent exponent) {
        if (exponent.isEqualTo(Exponents.ZERO))
            return ONE;
        if (exponent.isEqualTo(Exponents.ONE))
            return this;
        if (exponent.isLessThan(Exponents.ZERO))
            throw new UnsupportedOperationException();
        if (exponent.denominator() == 1) 
            return new BigDecimalFieldFixedScale2(value.pow((exponent.numerator())));
        if (exponent.denominator() % 2 != 0) 
            throw new UnsupportedOperationException();
        int n = exponent.denominator() / 2;
        BigDecimal v = value;
        MathContext mc = new MathContext(value.precision(),ROUNDING_MODE);
        for (int i=0; i < n; i++)
            v = v.sqrt(mc);
        if (exponent.numerator() != 1)
            v = v.pow(exponent.numerator());
        return new BigDecimalFieldFixedScale2(v);
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