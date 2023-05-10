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

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Factory class for creating expressions, scalars, and measures.
 * 
 * @author andreww1011
 */
public abstract class Expressions {
    
    /**
     * A scalar representing the number 0.
     */
    static final Scalar ZERO = take(0); //magic number
    
    /**
     * A scalar representing the number 1.
     */
    static final Scalar ONE = take(1); //magic number
    
    /**
     * A scalar representing the decimal number 10.
     */
    static final Scalar TEN = take(10); //magic number
    
    private Expressions(){}
    
    /**
     * Returns a scalar of the specified integer.  The returned implementation is
     * immutable and thread-safe.
     *
     * @param scalar a int
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    public static final Scalar take(int scalar) {
        return ScalarImpl.take(scalar);
    }
    
    /**
     * Returns a scalar of the number represented by the specified string.
     * The returned implementation is immutable and thread-safe.
     *
     * @param scalar a {@link java.lang.String} object
     * @return a {@link com.jamw.jmud.Scalar} object
     */
    public static final Scalar take(String scalar) {
        return ScalarImpl.take(scalar);
    }
    
    /**
     * Returns an expression representing the measure of the specified integer value and unit.
     * The returned implementation is immutable and thread-safe.
     *
     * @param value an integer number.
     * @param unit the unit of the specified number.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    public static final Expression take(int value, Unit unit) {
        return ExpressionImpl.take(value,unit);
    }
    
    /**
     * Returns an expression representing the measure of the number represented 
     * by the specified string and unit.
     * The returned implementation is immutable and thread-safe.
     *
     * @param value a string representing a number.
     * @param unit the unit of the specified number.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    public static final Expression take(String value, Unit unit) {
        return ExpressionImpl.take(value,unit);
    }
    
    /**
     * Returns an expression representing the measure of the specified scalar
     * and unit.
     * The returned implementation is immutable and thread-safe.
     *
     * @param value a scalar.
     * @param unit the unit of the specified scalar.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    public static final Expression take(Scalar value, Unit unit) {
        return ExpressionImpl.take(value,unit);
    }
    
    /**
     * Returns an expression representing the measure resulting from applying 
     * the specified function.
     * The returned implementation is immutable and thread-safe.
     * @param function a {@link Function} that takes a {@link Field.Factory} 
     *                 and produces a measure.
     * @param dimension the dimension of the measure produced by the specified funtion.
     * @return a {@link com.jamw.jmud.Expression} object
     */
    public static final Expression take(Function<Field.Factory,Measure> function, Dimension dimension) {
        return new ExpressionImpl(function,dimension);
    }
    
    /**
     * Returns a measure of the specified field and unit.
     * The returned implementation is immutable and thread-safe.
     *
     * @param <F> the type of the field of the measure.
     * @param value a field of type T.
     * @param unit the unit of the specified field.
     * @return a {@link com.jamw.jmud.Measure} object
     */
    public static final <F extends Field<F>> Measure<F> take(F value, Unit unit) {
        return MeasureImpl.take(value,unit);
    }
    
    private static final class ScalarImpl implements Scalar {

        private static final ScalarImpl take(int scalar) {
            String i = Integer.toString(scalar);
            return new ScalarImpl((Field.Factory factory) -> factory.of(scalar),i);
        }
        
        private static final ScalarImpl take(String scalar) {
            return new ScalarImpl((Field.Factory factory) -> factory.of(scalar),scalar);
        }
        
        private final Map<Field.Factory,Field> memo;
        private final Function<Field.Factory,Field> function;
        private final String toString;
        
        private ScalarImpl(Function<Field.Factory,Field> function,String toString) {
            this.memo = new ConcurrentHashMap<>();
            this.function = function;
            this.toString = toString;
        }
        
        @Override
        public String toString() {
            return toString;
        }
        
        @Override
        public Scalar negate() {
            Function<Field.Factory,Field> g = (factory) -> using(factory).negate();
            StringBuilder sb = new StringBuilder(toString.length()+3); //magic number
            sb.append("-(").append(toString).append(")");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Scalar reciprocal() throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).reciprocal();
            StringBuilder sb = new StringBuilder(toString.length()+6); //magic number
            sb.append("(1/(").append(toString).append("))");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Scalar add(int scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).add(factory.of(scalar));
            String i = Integer.toString(scalar);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 3); //magic number
            sb.append(toString).append(" + ").append(i);
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar add(String scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).add(factory.of(scalar));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.length() + 3); //magic number
            sb.append(toString).append(" + ").append(scalar);
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar add(Scalar scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).add(scalar.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.toString().length() + 3); //magic number
            sb.append(toString).append(" + ").append(scalar.toString());
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Expression add(Expression expression) {
            return ExpressionImpl.take(this,Units.UNITLESS).add(expression); 
        }
        
        @Override
        public <T extends Field<T>> T add(T value) {
            return using(value.getFactory()).add(value);
        }
        
        @Override
        public <T extends Field<T>> Measure<T> add(Measure<T> measure) {
            return ExpressionImpl.take(this,Units.UNITLESS).add(measure); 
        }

        @Override
        public Scalar subtract(int scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).subtract(factory.of(scalar));
            String i = Integer.toString(scalar);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 3); //magic number
            sb.append(toString).append(" - ").append(i);
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar subtract(String scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).subtract(factory.of(scalar));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.length() + 3); //magic number
            sb.append(toString).append(" - ").append(scalar);
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar subtract(Scalar scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).subtract(scalar.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.toString().length() + 3); //magic number
            sb.append(toString).append(" - ").append(scalar.toString());
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Expression subtract(Expression expression) {
            return ExpressionImpl.take(this,Units.UNITLESS).subtract(expression); 
        }
        
        @Override
        public <T extends Field<T>> T subtract(T value) {
            return using(value.getFactory()).subtract(value);
        }
        
        @Override
        public <T extends Field<T>> Measure<T> subtract(Measure<T> measure) {
            return ExpressionImpl.take(this,Units.UNITLESS).subtract(measure); 
        }
        
        @Override
        public Scalar multiply(int scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).multiply(factory.of(scalar));
            String i = Integer.toString(scalar);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 5); //magic number
            sb.append("(").append(toString).append(")*(").append(i).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar multiply(String scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).multiply(factory.of(scalar));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.length() + 5); //magic number
            sb.append("(").append(toString).append(")*(").append(scalar).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar multiply(Scalar scalar) {
            Function<Field.Factory,Field> g = (factory) -> using(factory).multiply(scalar.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.toString().length() + 5); //magic number
            sb.append("(").append(toString).append(")*(").append(scalar.toString()).append(")");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Expression multiply(int value,Unit unit) {
            return ExpressionImpl.take(this,Units.UNITLESS).multiply(value,unit);
        }

        @Override
        public Expression multiply(String value,Unit unit) {
            return ExpressionImpl.take(this,Units.UNITLESS).multiply(value,unit);
        }
        
        @Override
        public Expression multiply(Scalar value, Unit unit) {
            return ExpressionImpl.take(this,Units.UNITLESS).multiply(value,unit);
        }

        @Override
        public Expression multiply(Expression expression) {
            return ExpressionImpl.take(this,Units.UNITLESS).multiply(expression);
        }
        
        @Override
        public <T extends Field<T>> Measure<T> multiply(T value,Unit unit) {
            return multiply(MeasureImpl.take(value,unit));
        }

        @Override
        public <T extends Field<T>> Measure<T> multiply(Measure<T> measure) {
            T.Factory<T> factory = measure.getFactory();
            return MeasureImpl.take(using(factory),Units.UNITLESS).multiply(measure);
        }

        @Override
        public Scalar divide(int scalar) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).divide(factory.of(scalar));
            String i = Integer.toString(scalar);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 5); //magic number
            sb.append("(").append(toString).append(")/(").append(i).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar divide(String scalar) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).divide(factory.of(scalar));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.length() + 5); //magic number
            sb.append("(").append(toString).append(")/(").append(scalar).append(")");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Scalar divide(Scalar scalar) 
                throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).divide(scalar.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + scalar.toString().length() + 5); //magic number
            sb.append("(").append(toString).append(")/(").append(scalar.toString()).append(")");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Expression divide(int value,Unit unit) throws ArithmeticException {
            return ExpressionImpl.take(this,Units.UNITLESS).divide(value,unit);
        }

        @Override
        public Expression divide(String value,Unit unit) throws ArithmeticException {
            return ExpressionImpl.take(this,Units.UNITLESS).divide(value,unit);
        }
        
        @Override
        public Expression divide(Scalar value, Unit unit) {
            return ExpressionImpl.take(this,Units.UNITLESS).divide(value,unit);
        }

        @Override
        public Expression divide(Expression expression) throws ArithmeticException {
            return ExpressionImpl.take(this,Units.UNITLESS).divide(expression);
        }
        
        @Override
        public <T extends Field<T>> Measure<T> divide(T value,Unit unit)
                throws ArithmeticException {
            return divide(MeasureImpl.take(value,unit));
        }

        @Override
        public <T extends Field<T>> Measure<T> divide(Measure<T> measure)
                throws ArithmeticException {
            T.Factory<T> factory = measure.getFactory();
            return MeasureImpl.take(using(factory),Units.UNITLESS).divide(measure);
        }

        @Override
        public Scalar power(int exponent) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).power(factory.of(exponent));
            String i = Integer.toString(exponent);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 5); //magic number
            sb.append("(").append(toString).append(")^(").append(i).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar power(String exponent) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).power(factory.of(exponent));
            StringBuilder sb = new StringBuilder(toString.length() + exponent.length() + 5); //magic number
            sb.append("(").append(toString).append(")^(").append(exponent).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar power(Scalar exponent) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).power(exponent.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + exponent.toString().length() + 5); //magic number
            sb.append("(").append(toString).append(")^(").append(exponent.toString()).append(")");
            return new ScalarImpl(g,sb.toString());
        }
        
        @Override
        public Scalar power(Exponent exponent) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).power(exponent);
            String i = exponent.denominator() == 1 ? 
                       Integer.toString(exponent.numerator()) :
                       Integer.toString(exponent.numerator()) + "/" + Integer.toString(exponent.denominator());
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 5); //magic number
            sb.append("(").append(toString).append(")^(").append(i).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar logarithm(int base) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).logarithm(factory.of(base));
            String i = Integer.toString(base);
            StringBuilder sb = new StringBuilder(toString.length() + i.length() + 6); //magic number
            sb.append("log_").append(i).append("(").append(toString).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar logarithm(String base) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).logarithm(factory.of(base));
            StringBuilder sb = new StringBuilder(toString.length() + base.length() + 6); //magic number
            sb.append("log_").append(base).append("(").append(toString).append(")");
            return new ScalarImpl(g,sb.toString());
        }

        @Override
        public Scalar logarithm(Scalar base) throws ArithmeticException {
            Function<Field.Factory,Field> g = (factory) -> using(factory).logarithm(base.using(factory));
            StringBuilder sb = new StringBuilder(toString.length() + base.toString().length() + 6); //magic number
            sb.append("log_").append(base.toString()).append("(").append(toString).append(")");
            return new ScalarImpl(g,sb.toString());
        }
  
        @Override
        public <T extends Field<T>> T using(T.Factory<T> factory) {
            return (T)memo.computeIfAbsent(factory,(x) -> function.apply(x));
        }
    }
    
    private static final class ExpressionImpl implements Expression {
        
        private static final ExpressionImpl take(int value, Unit unit) {
            return new ExpressionImpl( (factory) -> 
                    MeasureImpl.take(value,unit,factory), unit.getDimension());
        }
        
        private static final ExpressionImpl take(String value, Unit unit) {
            return new ExpressionImpl( (factory) -> 
                    MeasureImpl.take(value,unit,factory), unit.getDimension());
        }
        
        private static final ExpressionImpl take(Scalar value, Unit unit) {
            return new ExpressionImpl( (factory) -> 
                    MeasureImpl.take(value.using(factory),unit), unit.getDimension());
        }
        
        private final Map<Field.Factory,Measure> memo;
        private final Function<Field.Factory,Measure> function;
        private final Dimension dimension;
        
        private ExpressionImpl(Function<Field.Factory,Measure> function, Dimension dimension) {
            this.memo = new ConcurrentHashMap<>();
            this.function = function;
            this.dimension = dimension;
        }
        
        @Override
        public Dimension getDimension() {
            return dimension;
        }

        @Override
        public Expression add(int value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).add(value,unit);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression add(String value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).add(value,unit);
            return new ExpressionImpl(g,getDimension());
        }
        
        @Override
        public Expression add(Scalar value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).add(value,unit);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public <T extends Field<T>> Measure<T> add(T value, Unit unit) {
            return add(MeasureImpl.take(value,unit));
        }

        @Override
        public <T extends Field<T>> Measure<T> add(Measure<T> measure) {
            Dimension.assertCommensurable(getDimension(),measure.getUnit().getDimension());
            return using(measure.getFactory()).add(measure);
        }

        @Override
        public Expression add(Expression expression) {
            Dimension.assertCommensurable(getDimension(),expression.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).add(expression);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression subtract(int value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).subtract(value,unit);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression subtract(String value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).subtract(value,unit);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression subtract(Scalar value, Unit unit) {
            Dimension.assertCommensurable(getDimension(),unit.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).subtract(value,unit);
            return new ExpressionImpl(g,getDimension());
        }
        
        @Override
        public <T extends Field<T>> Measure<T> subtract(T value, Unit unit) {
            return subtract(MeasureImpl.take(value,unit));
        }

        @Override
        public <T extends Field<T>> Measure<T> subtract(Measure<T> measure) {
            Dimension.assertCommensurable(getDimension(),measure.getUnit().getDimension());
            return using(measure.getFactory()).subtract(measure);
        }

        @Override
        public Expression subtract(Expression expression) {
            Dimension.assertCommensurable(getDimension(),expression.getDimension());
            Function<Field.Factory,Measure> g = (factory) -> using(factory).subtract(expression);
            return new ExpressionImpl(g,getDimension());
        }
        
        @Override
        public Expression multiply(int scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(scalar);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression multiply(String scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(scalar);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression multiply(Scalar scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(scalar);
            return new ExpressionImpl(g,getDimension());
        }
        
        @Override
        public <T extends Field<T>> Measure<T> multiply(T scalar) {
            return using(scalar.getFactory()).multiply(scalar);
        }

        @Override
        public Expression multiply(int value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension()).create();
            return new ExpressionImpl(g,d);
        }

        @Override
        public Expression multiply(String value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension()).create();
            return new ExpressionImpl(g,d);
        }
        
        @Override
        public Expression multiply(Scalar value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension()).create();
            return new ExpressionImpl(g,d);
        }

        @Override
        public <T extends Field<T>> Measure<T> multiply(T value,Unit unit) {
            return using(value.getFactory()).multiply(value,unit);
        }

        @Override
        public <T extends Field<T>> Measure<T> multiply(Measure<T> measure) {
            return using(measure.getFactory()).multiply(measure);
        }

        @Override
        public Expression multiply(Expression expression) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).multiply(expression);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(expression.getDimension()).create();
            return new ExpressionImpl(g,d);
        }

        @Override
        public Expression divide(int scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(scalar);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression divide(String scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(scalar);
            return new ExpressionImpl(g,getDimension());
        }

        @Override
        public Expression divide(Scalar scalar) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(scalar);
            return new ExpressionImpl(g,getDimension());
        }
        
        @Override
        public <T extends Field<T>> Measure<T> divide(T scalar) {
            return using(scalar.getFactory()).divide(scalar);
        }

        @Override
        public Expression divide(int value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension(),-1).create();
            return new ExpressionImpl(g,d);
        }

        @Override
        public Expression divide(String value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension(),-1).create();
            return new ExpressionImpl(g,d);
        }
        
        @Override
        public Expression divide(Scalar value,Unit unit) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(value,unit);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(unit.getDimension(),-1).create();
            return new ExpressionImpl(g,d);
        }

        @Override
        public <T extends Field<T>> Measure<T> divide(T value,Unit unit) {
            return using(value.getFactory()).divide(value,unit);
        }

        @Override
        public <T extends Field<T>> Measure<T> divide(Measure<T> measure) {
            return using(measure.getFactory()).divide(measure);
        }

        @Override
        public Expression divide(Expression expression) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).divide(expression);
            Dimension d = Dimensions.newDimension().append(getDimension()).append(expression.getDimension(),-1).create();
            return new ExpressionImpl(g,d);
        }
        
        @Override
        public Expression power(Exponent exponent) {
            Function<Field.Factory,Measure> g = (factory) -> using(factory).power(exponent);
            Dimension d = Dimensions.newDimension().append(getDimension(),exponent).create();
            return new ExpressionImpl(g,d);
        }
    
        @Override
        public <T extends Field<T>> Measure<T> using(T.Factory<T> factory) {
            return memo.computeIfAbsent(factory,(x) -> function.apply(x));
        }
    }
    
    private static final class MeasureImpl<F extends Field<F>> implements Measure<F> {

        private final F value;
        private final Unit unit;
        
        private MeasureImpl(F value,Unit unit) {
            this.value = value;
            this.unit = unit;
        }
        
        @Override
        public F getField() {
            return value;
        }

        @Override
        public Unit getUnit() {
            return unit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Measure))
                return false;
            Measure om = (Measure)o;
            if (!(om.getField().getClass().isAssignableFrom(this.value.getClass())))
                return false;
            F of = (F)om.getField();
            return this.unit.equals(om.getUnit())
                    && this.value.isEqualTo(of);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + Objects.hashCode(this.value);
            hash = 37 * hash + Objects.hashCode(this.unit);
            return hash;
        }
        
        @Override
        public int compareTo(Measure<F> measure) throws IncommensurableDimensionException {
            Dimension.assertCommensurable(unit.getDimension(),measure.getUnit().getDimension());
            F f1 = baseUnitValue(this);
            F f2 = baseUnitValue(measure);
            return f1.compareTo(f2);
        }
        
        private static <T extends Field<T>> T baseUnitValue(Measure<T> measure) {
            return measure.getField().multiply(measure.getUnit().getScale());
        }
        
        @Override
        public final String toString() {
            return value.toString() + " " + unit.getSymbol();
        }
        
        private static final <T extends Field<T>> Measure<T> take(int value, Unit unit, T.Factory<T> factory) {
            return new MeasureImpl<>(factory.of(value),unit);
        }
        
        private static final <T extends Field<T>> Measure<T> take(String value, Unit unit, T.Factory<T> factory) {
            return new MeasureImpl<>(factory.of(value),unit);
        }
        
        private static final <T extends Field<T>> Measure<T> take(T value, Unit unit) {
            return new MeasureImpl<>(value,unit);
        }
        
        @Override
        public Measure<F> add(int value, Unit unit) {
            return add(getFactory().of(value),unit);
        }
    
        @Override
        public Measure<F> add(String value, Unit unit) {
            return add(getFactory().of(value),unit);
        }
        
        @Override
        public Measure<F> add(Scalar value, Unit unit) {
            return add(value.using(getFactory()),unit);
        }

        @Override
        public Measure<F> add(F value, Unit unit) {
            return add(take(value,unit));
        }

        @Override
        public Measure<F> add(Measure<F> measure) {
            F v = getField().add(measure.as(getUnit()).getField());
            return take(v,getUnit());
        }

        @Override
        public Measure<F> add(Expression expression) {
            return add(expression.using(getFactory()));
        }

        @Override
        public Measure<F> subtract(int value, Unit unit) {
            return subtract(getFactory().of(value),unit);
        }
    
        @Override
        public Measure<F> subtract(String value, Unit unit) {
            return subtract(getFactory().of(value),unit);
        }
        
        @Override
        public Measure<F> subtract(Scalar value, Unit unit) {
            return subtract(value.using(getFactory()),unit);
        }

        @Override
        public Measure<F> subtract(F value, Unit unit) {
            return subtract(take(value,unit));
        }

        @Override
        public Measure<F> subtract(Measure<F> measure) {
            F v = getField().subtract(measure.as(getUnit()).getField());
            return take(v,getUnit());
        }

        @Override
        public Measure<F> subtract(Expression expression) {
            return subtract(expression.using(getFactory()));
        }
        
        @Override
        public Measure<F> multiply(int scalar) {
            return multiply(scalar,Units.UNITLESS);
        }

        @Override
        public Measure<F> multiply(String scalar) {
            return multiply(scalar,Units.UNITLESS);
        }
        
        @Override
        public Measure<F> multiply(Scalar scalar) {
            return multiply(scalar.using(getFactory()),Units.UNITLESS);
        }

        @Override
        public Measure<F> multiply(F scalar) {
            return multiply(scalar,Units.UNITLESS);
        }

        @Override
        public Measure<F> multiply(int value,Unit unit) {
            return multiply(getFactory().of(value),unit);
        }

        @Override
        public Measure<F> multiply(String value,Unit unit) {
            return multiply(getFactory().of(value),unit);
        }
        
        @Override
        public Measure<F> multiply(Scalar value,Unit unit) {
            return multiply(value.using(getFactory()),unit);
        }

        @Override
        public Measure<F> multiply(F value,Unit unit) {
            return multiply(take(value,unit));
        }

        @Override
        public Measure<F> multiply(Measure<F> measure) {
            F v = getField().multiply(measure.getField());
            Unit u = Units.newUnit().as(getUnit()).multiply(measure.getUnit()).create();
            return take(v,u);
        }

        @Override
        public Measure<F> multiply(Expression expression) {
            return multiply(expression.using(getFactory()));
        }

        @Override
        public Measure<F> divide(int scalar) {
            return divide(scalar,Units.UNITLESS);
        }

        @Override
        public Measure<F> divide(String scalar) {
            return divide(scalar,Units.UNITLESS);
        }
        
        @Override
        public Measure<F> divide(Scalar scalar) {
            return divide(scalar.using(getFactory()),Units.UNITLESS);
        }

        @Override
        public Measure<F> divide(F scalar) {
            return divide(scalar,Units.UNITLESS);
        }

        @Override
        public Measure<F> divide(int value,Unit unit) {
            return divide(getFactory().of(value),unit);
        }

        @Override
        public Measure<F> divide(String value,Unit unit) {
            return divide(getFactory().of(value),unit);
        }
        
        @Override
        public Measure<F> divide(Scalar value,Unit unit) {
            return divide(value.using(getFactory()),unit);
        }

        @Override
        public Measure<F> divide(F value,Unit unit) {
            return divide(take(value,unit));
        }

        @Override
        public Measure<F> divide(Measure<F> measure) {
            F v = getField().divide(measure.getField());
            Unit u = Units.newUnit().as(getUnit()).divide(measure.getUnit()).create();
            return take(v,u);
        }

        @Override
        public Measure<F> divide(Expression expression) {
            return divide(expression.using(getFactory()));
        }
        
        @Override
        public Measure<F> power(Exponent exponent) {
            F v = getField().power(exponent);
            Unit u = Units.newUnit().as(getUnit(),exponent).create();
            return take(v,u);
        }

        @Override
        public Measure<F> as(Unit unit) {
            Dimension.assertCommensurable(getUnit().getDimension(),unit.getDimension());
            F v = getField().multiply(getUnit().getScale().divide(unit.getScale()));
            return new MeasureImpl<>(v,unit);
        }
    }
}
