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

import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 *
 * @author andreww1011
 */
public abstract class Scales {
    
    /**
     * Scale of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature},
     * defined as:
     * <p>
     * y celsius = x {@link #KELVIN kelvin} - 273.15
     * <p>
     * Denoted as <i>"C"</i>.
     */
    public static final Scale CELSIUS;
    /**
     * Scale of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature},
     * defined as:
     * <p>
     * y fahrenheit = x {@link #RANKINE rankine} - 459.67
     * <p>
     * Denoted as <i>F</i>.
     */
    public static final Scale FAHRENHEIT;
    
    static {
        CELSIUS         = new ScaleImpl("CELSIUS","C",Units.KELVIN,
                                (Field k) -> k.subtract(k.getFactory().of("273.15")),
                                (Field c) -> c.add(c.getFactory().of("273.15")));
        FAHRENHEIT       = new ScaleImpl("FAHRENHEIT","F",Units.RANKINE,
                                (Field r) -> r.subtract(r.getFactory().of("459.67")),
                                (Field f) -> f.add(f.getFactory().of("459.67")));
    }
    
    private Scales() {}
    
    public static final Scale bel(Unit referenceUnit) {
        return bel(referenceUnit,"BEL-" + referenceUnit.getName(),"B" + referenceUnit.getSymbol());
    }
    
    private static UnaryOperator<Field> BEL_FUNCTION = (f) -> f.logarithm(f.getFactory().of(10));
    private static UnaryOperator<Field> BEL_INVERSE_FUNCTION = (f) -> f.getFactory().of(10).power(f);
    
    public static final Scale bel(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,BEL_FUNCTION,BEL_INVERSE_FUNCTION);
    }
    
    private static UnaryOperator<Field> DECIBEL_FUNCTION = (f) -> BEL_FUNCTION.apply(f).multiply(f.getFactory().of(10));
    private static UnaryOperator<Field> DECIBEL_INVERSE_FUNCTION = (f) -> BEL_INVERSE_FUNCTION.apply(f.divide(f.getFactory().of(10)));
    
    public static final Scale decibel(Unit referenceUnit) {
        return bel(referenceUnit,"DECIBEL-" + referenceUnit.getName(),"dB" + referenceUnit.getSymbol());
    }
    
    public static final Scale decibel(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,DECIBEL_FUNCTION,DECIBEL_INVERSE_FUNCTION);
    }
    
    public static final Scale neper(Unit referenceUnit) {
        return bel(referenceUnit,"NEPER-" + referenceUnit.getName(),"Np" + referenceUnit.getSymbol());
    }
    
    private static UnaryOperator<Field> NEPER_FUNCTION = (f) -> f.logarithm(Constants.euler.using(f.getFactory()));
    private static UnaryOperator<Field> NEPER_INVERSE_FUNCTION = (f) -> Constants.euler.using(f.getFactory()).power(f);
    
    public static final Scale neper(Unit referenceUnit, String name, String symbol) {
        return new ScaleImpl(name,symbol,referenceUnit,NEPER_FUNCTION,NEPER_INVERSE_FUNCTION);
    }
    
    private static abstract class AbstractScale {
        
        private final String name,symbol;
        
        protected AbstractScale(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }
        
        public final String getName() {
            return name;
        }

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
    }
    
    private static final class ScaleImpl extends AbstractScale implements Scale {

        private final Unit refUnit;
        private final UnaryOperator<Field> function;
        private final UnaryOperator<Field> inverseFunction;
        
        private ScaleImpl(String name,String symbol,Unit refUnit,UnaryOperator<Field> function,UnaryOperator<Field> inverseFunction) {
            super(name,symbol);
            this.refUnit = refUnit;
            this.function = function;
            this.inverseFunction = inverseFunction;
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
        public F getValue() {
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
                return value.compareTo(o.getValue());
            if (getScale().getReferenceUnit().getDimension().isCommensurable(o.getScale().getReferenceUnit().getDimension()))
                return getMeasure().compareTo(o.getMeasure());
            throw new IncommensurableDimensionException();
        }
        
        @Override
        public boolean equals(Object o) {
            return super.equals(o);
        }
        
        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        @Override
        public String toString() {
            return value.toString() + " " + scale.getSymbol();
        }
    }
}
