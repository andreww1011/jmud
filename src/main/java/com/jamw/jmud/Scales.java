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

import java.util.function.UnaryOperator;
import static com.jamw.jmud.Constants.euler;
import static com.jamw.jmud.Constants.ten;
import java.util.Objects;

/**
 * Factory class of common scales of measurement.
 * 
 * @author andreww1011
 */
public abstract class Scales {
    
    /**
     * Scale of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature},
     * defined as:
     * <p>
     * y celsius = x {@link Units#KELVIN kelvin} - 273.15
     * <p>
     * Denoted as <i>"C"</i>.
     */
    public static final Scale CELSIUS;
    /**
     * Scale of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature},
     * defined as:
     * <p>
     * y fahrenheit = x {@link Units#RANKINE rankine} - 459.67
     * <p>
     * Denoted as <i>"F"</i>.
     */
    public static final Scale FAHRENHEIT;
    
    static {
        Scalar cs = Expressions.take("273.15");
        CELSIUS         = new ScaleImpl("CELSIUS","C",Units.KELVIN,
                                (Field k) -> k.subtract(cs.using(k.getFactory())),
                                (Field c) -> c.add(cs.using(c.getFactory())));
        Scalar fs = Expressions.take("459.67");
        FAHRENHEIT       = new ScaleImpl("FAHRENHEIT","F",Units.RANKINE,
                                (Field r) -> r.subtract(fs.using(r.getFactory())),
                                (Field f) -> f.add(fs.using(f.getFactory())));
    }
    
    private Scales() {}
    
    /**
     * Returns a bel scale of the referenced unit.  Prepends <i>"BEL-"</i> and <i>"B</i>
     * to the reference unit name and symbol, respectively, for the name and 
     * symbol of this scale.
     * 
     * <p>The bel scale expresses the ratio of two measures on a base decimal 10
     * logarithmic scale.  Levels that differ by one bel have a ratio of 10.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in bel, <i>L<sub>bel</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>bel</sub> = log<sub>10</sub>(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @return a bel scale of the specified unit with "BEL-" and "B" prepended to
     * the name and symbol, respectively.
     */
    public static final Scale bel(Unit referenceUnit) {
        return bel(referenceUnit,"BEL-" + referenceUnit.getName(),"B" + referenceUnit.getSymbol());
    }
    
    private static UnaryOperator<Field> BEL_FUNCTION = (f) -> f.logarithm(ten.using(f.getFactory()));
    private static UnaryOperator<Field> BEL_INVERSE_FUNCTION = (f) -> ten.using(f.getFactory()).power(f);
    
    /**
     * Returns a bel scale of the referenced unit with the specified name and 
     * symbol.  The specified name and symbol cannot be <code>null</code> nor blank.
     * 
     * <p>The bel scale expresses the ratio of two measures on a base decimal 10
     * logarithmic scale.  Levels that differ by one bel have a ratio of 10.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in bel, <i>L<sub>bel</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>bel</sub> = log<sub>10</sub>(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @param name the name for the scale.
     * @param symbol the symbol for the scale.
     * @return a bel scale of the specified unit with the specified name and symbol.
     */
    public static final Scale bel(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,BEL_FUNCTION,BEL_INVERSE_FUNCTION);
    }
    
    private static UnaryOperator<Field> DECIBEL_FUNCTION = (f) -> BEL_FUNCTION.apply(f).multiply(ten.using(f.getFactory()));
    private static UnaryOperator<Field> DECIBEL_INVERSE_FUNCTION = (f) -> BEL_INVERSE_FUNCTION.apply(f.divide(ten.using(f.getFactory())));
    
    /**
     * Returns a decibel scale of the referenced unit.  Prepends <i>"DECIBEL-"</i> and <i>"dB</i>
     * to the reference unit name and symbol, respectively, for the name and 
     * symbol of this scale.
     * 
     * <p>The decibel scale expresses the ratio of two measures on a base decimal 10
     * logarithmic scale.  Levels that differ by one decibel have a ratio of 10<sup>1/10</sup> \u2248 1.26.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in decibel, <i>L<sub>decibel</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>decibel</sub> = 10•log<sub>10</sub>(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @return a decibel scale of the specified unit with "DECIBEL-" and "dB" prepended to
     * the name and symbol, respectively.
     */
    public static final Scale decibel(Unit referenceUnit) {
        return decibel(referenceUnit,"DECIBEL-" + referenceUnit.getName(),"dB" + referenceUnit.getSymbol());
    }
    
    /**
     * Returns a decibel scale of the referenced unit with the specified name and 
     * symbol.  The specified name and symbol cannot be <code>null</code> nor blank.
     * 
     * <p>The decibel scale expresses the ratio of two measures on a base decimal 10
     * logarithmic scale.  Levels that differ by one decibel have a ratio of 10<sup>1/10</sup> \u2248 1.26.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in decibel, <i>L<sub>decibel</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>decibel</sub> = 10•log<sub>10</sub>(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @param name the name for the scale.
     * @param symbol the symbol for the scale.
     * @return a decibel scale of the specified unit with the specified name and symbol.
     */
    public static final Scale decibel(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,DECIBEL_FUNCTION,DECIBEL_INVERSE_FUNCTION);
    }
    
    /**
     * Returns a neper scale of the referenced unit.  Prepends <i>"NEPER-"</i> and <i>"Np</i>
     * to the reference unit name and symbol, respectively, for the name and 
     * symbol of this scale.
     * 
     * <p>The neper scale expresses the ratio of two measures on a base <i>{@linkplain Constants#euler e}</i>
     * logarithmic scale.  Levels that differ by one neper have a ratio of <i>e</i> \u2248 2.72.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in neper, <i>L<sub>neper</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>neper</sub> = ln(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @return a neper scale of the specified unit with "NEPER-" and "Np" prepended to
     * the name and symbol, respectively.
     */
    public static final Scale neper(Unit referenceUnit) {
        return neper(referenceUnit,"NEPER-" + referenceUnit.getName(),"Np" + referenceUnit.getSymbol());
    }
    
    private static UnaryOperator<Field> NEPER_FUNCTION = (f) -> f.logarithm(euler.using(f.getFactory()));
    private static UnaryOperator<Field> NEPER_INVERSE_FUNCTION = (f) -> euler.using(f.getFactory()).power(f);
    
    /**
     * Returns a neper scale of the referenced unit with the specified name and 
     * symbol.  The specified name and symbol cannot be <code>null</code> nor blank.
     * 
     * <p>The neper scale expresses the ratio of two measures on a base <i>{@linkplain Constants#euler e}</i>
     * logarithmic scale.  Levels that differ by one neper have a ratio of <i>e</i> \u2248 2.72.
     * 
     * <p>Given a reference unit, <i>U<sub>ref</sub></i>, and a measure with a 
     * commensurable unit, <i>M</i>, the level in neper, <i>L<sub>neper</sub></i>, is calculated by:
     * 
     * <p><i>L<sub>neper</sub> = ln(M/{1 U<sub>ref</sub>})</i>,
     * 
     * <p>where the braces {} indicate the measure of exactly 1 unit of <i>U<sub>ref</sub></i>.
     * 
     * @param referenceUnit a unit.
     * @param name the name for the scale.
     * @param symbol the symbol for the scale.
     * @return a neper scale of the specified unit with the specified name and symbol.
     */
    public static final Scale neper(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,NEPER_FUNCTION,NEPER_INVERSE_FUNCTION);
    }
    
    private static final class ScaleImpl implements Scale {

        private final String name,symbol;
        private final Unit refUnit;
        private final UnaryOperator<Field> function;
        private final UnaryOperator<Field> inverseFunction;
        
        private ScaleImpl(String name,String symbol,Unit refUnit,UnaryOperator<Field> function,UnaryOperator<Field> inverseFunction) {
            check(name);
            check(symbol);
            this.name = name;
            this.symbol = symbol;
            this.refUnit = refUnit;
            this.function = function;
            this.inverseFunction = inverseFunction;
        }
        
        private static void check(String s) {
            Objects.requireNonNull(s);
            if (s.isBlank())
                throw new IllegalArgumentException();
        }

        @Override
        public final String getName() {
            return name;
        }

        @Override
        public final String getSymbol() {
            return symbol;
        }
        
        @Override
        public final boolean equals(Object o) {
            return super.equals(o);
        }
        
        @Override
        public final int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public Unit getReferenceUnit() {
            return refUnit;
        }
        
        @Override
        public String toString() {
            return "Scale: " + getName() + " (" + getSymbol() + ")";
        }
        
        @Override
        public <T extends Field<T>> Level<T> level(Measure<T> measure) {
            Measure<T> m = measure.as(refUnit);
            T levelValue = (T)function.apply(m.getField());
            return new LevelImpl<>(levelValue,this,m);
        }
        
        @Override
        public <T extends Field<T>> Level<T> of(T value) {
            T measureValue = (T)inverseFunction.apply(value);
            Measure<T> measure = Expressions.take(measureValue,refUnit);
            return new LevelImpl<>(value,this,measure);
        }
    }
    
    private static final class LevelImpl<F extends Field<F>> implements Level<F> {

        private final F value;
        private final Scale scale;
        private final Measure<F> measure;
        
        private LevelImpl(F value, Scale scale, Measure<F> measure) {
            this.value = value;
            this.scale = scale;
            this.measure = measure;
        }
        
        @Override
        public F getField() {
            return value;
        }

        @Override
        public Scale getScale() {
            return scale;
        }
        
        @Override
        public Measure<F> getMeasure() {
            return measure;
        }

        @Override
        public int compareTo(Level<F> o) 
                throws IncommensurableDimensionException {
            if (getScale().equals(o.getScale()))
                return value.compareTo(o.getField());
            if (getScale().getReferenceUnit().getDimension().isCommensurable(o.getScale().getReferenceUnit().getDimension()))
                return getMeasure().compareTo(o.getMeasure());
            throw new IncommensurableDimensionException();
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Level))
                return false;
            Level ol = (Level)o;
            if (!(ol.getField().getClass().isAssignableFrom(this.value.getClass())))
                return false;
            F of = (F)ol.getField();
            return this.scale.equals(ol.getScale())
                    && this.value.isEqualTo(of);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + Objects.hashCode(this.value);
            hash = 29 * hash + Objects.hashCode(this.scale);
            return hash;
        }
        
        @Override
        public String toString() {
            return value.toString() + " " + scale.getSymbol();
        }
    }
}
