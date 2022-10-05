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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andreww1011
 */
public interface FundamentalUnitContract extends ObjectEqualityContract<FundamentalUnit> {
    
    static Scalar ONE = new Scalar() {
        @Override
        public Scalar negate() {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar reciprocal() {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar add(int scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar add(String scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar add(Scalar scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression add(Expression expression) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> T add(T value) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> add(Measure<T> measure) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar subtract(int scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar subtract(String scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar subtract(Scalar scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression subtract(Expression expression) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> T subtract(T value) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> subtract(Measure<T> measure) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar multiply(int scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar multiply(String scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar multiply(Scalar scalar) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression multiply(int value,Unit unit) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression multiply(String value,Unit unit) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression multiply(Scalar value,Unit unit) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression multiply(Expression expression) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> multiply(T value,Unit unit) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> multiply(Measure<T> measure) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar divide(int scalar) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar divide(String scalar) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar divide(Scalar scalar) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression divide(int value,Unit unit) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression divide(String value,Unit unit) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression divide(Scalar value,Unit unit) {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Expression divide(Expression expression) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> divide(T value,Unit unit) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar power(int exponent) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar power(String exponent) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar power(Scalar exponent) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar power(Exponent exponent) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar logarithm(int base) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar logarithm(String base) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public Scalar logarithm(Scalar base) throws ArithmeticException {
            throw new UnsupportedOperationException("Not implemented.");
        }

        @Override
        public <T extends Field<T>> T using(Field.Factory<T> factory) {
            return factory.of(1);
        }  
    };
    
    Field.Factory<?> getFieldFactory();
    
    FundamentalUnit getFundamentalUnit();
    
    FundamentalDimension expectedFundamentalDimension();
    
    @Override
    default FundamentalUnit createObject() {
        return getFundamentalUnit();
    }
    
    String expectedName();
    
    String expectedSymbol();
    
    @Test
    default void testFundamentalUnitName() {
        assertEquals(getFundamentalUnit().getName(),expectedName());
    }
    
    @Test
    default void testFundamentalUnitSymbol() {
        assertEquals(getFundamentalUnit().getSymbol(),expectedSymbol());
    }
    
    @Test
    default void testFundamentalUnitDimension() {
        assertEquals(getFundamentalUnit().getDimension(),expectedFundamentalDimension());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    default void testFundamentalUnitScaleFactorValueOfOne() {
        final Field.Factory factory = getFieldFactory();
        assertTrue(getFundamentalUnit().getScale().using(factory).isEqualTo(ONE.using(factory)));
    }
}
