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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author andreww1011
 */
public abstract class Dimensions {
    
    private Dimensions(){}
    
    /**
     * Fundamental dimension representing the special null-value dimension.
     * <p>
     * Denoted as <i>[-]</i>
     */
    public static final FundamentalDimension DIMENSIONLESS = FundamentalDimension.DIMENSIONLESS;
    
    /**
     * Fundamental dimension of <i>mass</i>, the abstraction of the 
     * notion of a body's resistance to change in motion when a net force
     * is applied.
     * <p>
     * Denoted as <i>[M]</i>
     */
    public static final FundamentalDimension MASS                    
    = newFundamentalDimension("MASS","M");
    
    /**
     * Fundamental dimension of <i>length</i>, the abstraction of the
     * perception of space.
     * <p>
     * Denoted as <i>[L]</i>
     */
    public static final FundamentalDimension LENGTH                  
    = newFundamentalDimension("LENGTH","L");
    
    /**
     * Fundamental dimension of <i>time</i>, the abstraction of the perception
     * of sequences of events that occur in irreversible succession from past
     * to present.
     * <p>
     * Denoted as <i>[T]</i>
     */
    public static final FundamentalDimension TIME                    
    = newFundamentalDimension("TIME","T");
    
    /**
     * Fundamental dimension of <i>electric current</i>, the abstraction of the
     * notion of flow of electric charge.
     * <p>
     * Denoted as <i>[I]</i>
     */
    public static final FundamentalDimension ELECTRIC_CURRENT        
    = newFundamentalDimension("ELECTRIC CURRENT","I");
    
    /**
     * Fundamental dimension of <i>thermodynamic temperature</i>, the 
     * abstraction of the notion of the random vibrations of particle
     * constituents of matter.
     * <p>
     * Denoted as <i>[θ]</i>
     */
    public static final FundamentalDimension THERMODYNAMIC_TEMPERATURE
    = newFundamentalDimension("THERMODYNAMIC TEMPERATURE","θ");
    
    /**
     * Fundamental dimension of <i>amount of substance</i>, the abstraction
     * of the notion of the number of particles comprising matter.
     * <p>
     * Denoted as <i>[N]</i>
     */
    public static final FundamentalDimension AMOUNT_OF_SUBSTANCE
    = newFundamentalDimension("AMOUNT OF SUBSTANCE","N");
    
    /**
     * Fundamental dimension of <i>luminous intensity</i>, the abstraction
     * of the notion of light power through space.
     * <p>
     * Denoted as <i>[J]</i>
     */
    public static final FundamentalDimension LUMINOUS_INTENSITY
    = newFundamentalDimension("LUMINOUS INTENSITY","J");


    public static final FundamentalDimension newFundamentalDimension(String name, String symbol) {
        return new FundamentalDimensionImpl(name, symbol);
    }
    
    private static abstract class AbstractComposition implements Composition {
        
        private boolean isHashcodeCalced, isToStringCalced;
        private int hashcode;
        private String toString;
        
        private AbstractComposition() {
            isHashcodeCalced = false;
            isToStringCalced = false;
        }
        
        @Override
        public final int hashCode() {
            if (!isHashcodeCalced) {
                hashcode = calcHashcode();
                isHashcodeCalced = true;
            }
            return hashcode;
        }
        
        private static final int i = 7, j = 17;
        
        private int calcHashcode() {
            int hash = i;
            for (CompositionComponent cc : this) {
                if (!cc.getExponent().isEqualTo(Exponents.ZERO)) {
                    hash = j * hash + Objects.hashCode(cc);
                }
            }
            return hash;
        }
        
        @Override
        public final boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Composition))
                return false;
            Composition co = (Composition)o;
            for (CompositionComponent cc : this) {
                if (!cc.getExponent().isEqualTo(co.getExponent(cc.getFundamentalDimension())))
                    return false;
            }
            for (CompositionComponent cc : co) {
                if (!cc.getExponent().isEqualTo(this.getExponent(cc.getFundamentalDimension())))
                    return false;
            }
            return true;
        }
        
        @Override
        public final String toString() {
            if (!isToStringCalced) {
                toString = calcToString(this);
                isToStringCalced = true;
            }
            return toString;
        }
        
        private static String calcToString(Composition c) {
            StringBuilder sb = new StringBuilder();
            Iterator<CompositionComponent> iter = c.iterator();
            while (iter.hasNext()) {
                CompositionComponent cc = iter.next();
                FundamentalDimension fd = cc.getFundamentalDimension();
                Exponent e = cc.getExponent();
                if (!e.equals(Exponents.ZERO)) {
                    sb.append(fd.getSymbol());
                    if (!e.isEqualTo(Exponents.ONE)) {
                        sb.append("^").append(e.numerator());
                        if (e.denominator() != 1) {
                            sb.append("/").append(e.denominator());
                        }
                    }
                    if (iter.hasNext()) {
                        sb.append(" ");
                    }
                }
            }
            return sb.toString();
        }
    }
    
    private static abstract class AbstractCompositionComponent implements CompositionComponent {
        
        private AbstractCompositionComponent() {}
        
        @Override
        public final boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof CompositionComponent))
                return false;
            CompositionComponent c = (CompositionComponent)o;
            return this.getFundamentalDimension().equals(c.getFundamentalDimension())
                    && this.getExponent().isEqualTo(c.getExponent());
        }
        
        private static final int i = 5, j = 23; //magic number
        
        @Override
        public final int hashCode() {
            int hash = i;
            hash = j * hash + Objects.hashCode(getFundamentalDimension());
            hash = j * hash + Objects.hashCode(getExponent());
            return hash;
        }
        
        @Override
        public final String toString() {
            return new StringBuilder()
                .append("Fundamental Dimension: ")
                .append(getFundamentalDimension().getName())
                .append(" (")
                .append(getFundamentalDimension().getSymbol())
                .append(") , ")
                .append("Exponent: ")
                .append(getExponent().toString())
                .toString();
        }
    }
    
    private static abstract class AbstractDimension implements Dimension {

        private final String name, symbol;
        
        private AbstractDimension(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
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
            return this == o;
        }
        
        @Override
        public final int hashCode() {
            return super.hashCode();
        }
    }
    
    private static final class FundamentalDimensionImpl 
            extends AbstractDimension
            implements FundamentalDimension {
        
        private final Composition composition;
        
        private FundamentalDimensionImpl(String name, String symbol) {
            super(name,symbol);
            this.composition = new FundamentalDimensionCompositionImpl();
        }
        
        private final class FundamentalDimensionCompositionImpl extends AbstractComposition {
            
            private final CompositionComponent cc;
            
            private FundamentalDimensionCompositionImpl() {
                super();
                cc = new FundamentalDimensionCompositionComponentImpl();
            }
            
            private final class FundamentalDimensionCompositionComponentImpl extends AbstractCompositionComponent {
                @Override
                public FundamentalDimension getFundamentalDimension() {
                    return FundamentalDimensionImpl.this;
                }
                @Override
                public Exponent getExponent() {
                    return Exponents.ONE;
                }
            }
            
            @Override
            public Exponent getExponent(FundamentalDimension d) {
                return FundamentalDimensionImpl.this.equals(d) ? Exponents.ONE : Exponents.ZERO;
            }

            @Override
            public Iterator<CompositionComponent> iterator() {
                return new FundamentalDimensionComponentIteratorImpl();
            }
            
            private final class FundamentalDimensionComponentIteratorImpl 
                    implements Iterator<CompositionComponent> {
                private boolean hasNext;
                private FundamentalDimensionComponentIteratorImpl() {
                    hasNext = true;
                }
                @Override
                public boolean hasNext() {
                    return hasNext;
                }
                @Override
                public CompositionComponent next() {
                    if (hasNext()) {
                        hasNext = false;
                        return cc;
                    }
                    else
                        throw new NoSuchElementException();
                }
            }
        }
        
        @Override
        public Composition getComposition() {
            return composition;
        }
        
        @Override
        public final String toString() {
            return "Fundamental Dimension: " + getName() + " [" + getSymbol() + "]";
        }
    }
    
    public static final DimensionBuilder newDimension() {
        return new DimensionBuilderImpl();
    }
    
    private static final class DimensionBuilderImpl
            implements DimensionBuilder {

        private final String name, symbol;
        private final Map<FundamentalDimension,Exponent> compositionMap;
        
        DimensionBuilderImpl() {
            name = "";
            symbol = "";
            compositionMap = new HashMap<>();
        }
        
        private DimensionBuilderImpl(Map<FundamentalDimension,Exponent> compositionMap,
                                     String name,
                                     String symbol) {
            this.compositionMap = compositionMap;
            this.name = name;
            this.symbol = symbol;
        }
        
        @Override
        public DimensionBuilder append(Dimension d) {
            return append(d,1); //magic number
        }

        @Override
        public DimensionBuilder append(Dimension d,int exponentNumerator) {
            return append(d,exponentNumerator,1); //magic number
        }
        
        @Override
        public DimensionBuilder append(Dimension d,int exponentNumerator, int exponentDenominator) {
            return append(d,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public DimensionBuilder append(Dimension d,Exponent e) {
            return new DimensionBuilderImpl(put(d,e),name,symbol);
        }
        
        private Map<FundamentalDimension,Exponent> put(Dimension d, Exponent e) {
            Map<FundamentalDimension,Exponent> m = copy(compositionMap);
            Exponent dPow;
            FundamentalDimension fd;
            Exponent fe;
            for (CompositionComponent c : d.getComposition()) {
                fd = c.getFundamentalDimension();
                if (!fd.equals(Dimensions.DIMENSIONLESS)) {
                    fe = c.getExponent();
                    dPow = Exponents.power(fe, e);
                    if (m.containsKey(fd))
                        m.put(fd, Exponents.product(m.get(fd), dPow));
                    else
                        m.put(fd, dPow);
                }
            }
            return m;
        }
        
        private static Map<FundamentalDimension,Exponent> copy(Map<FundamentalDimension,Exponent> cMap) {
            return new HashMap<>(cMap);
        }

        @Override
        public DimensionBuilder withName(String name) {
            return new DimensionBuilderImpl(copy(compositionMap),name,symbol);
        }

        @Override
        public DimensionBuilder withSymbol(String symbol) {
            return new DimensionBuilderImpl(copy(compositionMap),name,symbol);
        }

        @Override
        public Dimension create() {
            return createDimension(copy(compositionMap),name,symbol);
        }
        
        private static Dimension createDimension(
                Map<FundamentalDimension,Exponent> map,
                String name,
                String symbol) {
            cleanCompositionMap(map);
            Composition composition = new CompositionImpl(map);
            Dimension d = new DimensionImpl(composition,name,symbol);
            String n = formatName(name,composition);
            String s = formatSymbol(symbol,composition);
            return new DimensionImpl(composition,n,s);
        }
        
        private static Map<FundamentalDimension,Exponent> cleanCompositionMap(Map<FundamentalDimension,Exponent> map) {
            removeZeroExponents(map);
            if (map.isEmpty())
                map.put(Dimensions.DIMENSIONLESS, Exponents.ONE);
            return map;
        }
        
        private static void removeZeroExponents(Map<FundamentalDimension,Exponent> map) {
            map.entrySet().stream()
                    .filter((e) -> !e.getKey().equals(Dimensions.DIMENSIONLESS) && !e.getValue().isEqualTo(Exponents.ZERO))
                    .collect(Collectors.toMap((e) -> e.getKey(),(e) -> e.getValue()));
        }
        
        private static String formatName(String name, Composition c) {
            if (isBlank(name)) {
                return calcName(c);
            }
            return name;
        }
        
        private static boolean isBlank(String s) {
            return s == null || s.isBlank();
        }

        private static String formatSymbol(String symbol, Composition c) {
            if (isBlank(symbol)) {
                return calcSymbol(c);
            }
            return symbol;
        }
        
        private static String calcName(Composition c) {
            StringBuilder sb = new StringBuilder();
            Iterator<CompositionComponent> iter = c.iterator();
            while (iter.hasNext()) {
                CompositionComponent cc = iter.next();
                FundamentalDimension fd = cc.getFundamentalDimension();
                Exponent e = cc.getExponent();
                if (!e.equals(Exponents.ZERO)) {
                    sb.append(fd.getName()).append(": ").append(e.numerator());
                    if (e.denominator() != 1) {
                        sb.append("/").append(e.denominator());
                    }
                    if (iter.hasNext()) {
                        sb.append("; ");
                    }
                }
            }
            return sb.toString();
        }
        
        private static String calcSymbol(Composition c) {
            return AbstractComposition.calcToString(c);
        }
    }

    private static final class CompositionImpl extends AbstractComposition {

        private final Map<FundamentalDimension,Exponent> compositionMap;

        private CompositionImpl(Map<FundamentalDimension,Exponent> compositionMap) {
            super();
            this.compositionMap = compositionMap;
        }

        @Override
        public Exponent getExponent(FundamentalDimension d) {
            return compositionMap.getOrDefault(d,Exponents.ZERO);
        }

        @Override
        public Iterator<CompositionComponent> iterator() {
            return new CompositionIteratorImpl();
        }

        private final class CompositionIteratorImpl implements Iterator<CompositionComponent> {

            private final Iterator<FundamentalDimension> iter;

            private CompositionIteratorImpl() {
                iter = compositionMap.keySet().iterator();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("A composition component cannot be removed.");
            }

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public CompositionComponent next() {
                FundamentalDimension fd = iter.next();
                Exponent e = compositionMap.get(fd);
                return new CompositionComponentImpl(fd,e);
            }
        }
    }

    private static final class CompositionComponentImpl extends AbstractCompositionComponent {

        private final FundamentalDimension fd;
        private final Exponent e;

        private CompositionComponentImpl(FundamentalDimension fd, Exponent e) {
            super();
            this.fd = fd;
            this.e = e;
        }

        @Override
        public FundamentalDimension getFundamentalDimension() {
            return fd;
        }

        @Override
        public Exponent getExponent() {
            return e;
        }
    }

    private static final class DimensionImpl extends AbstractDimension {

        private final Composition composition;

        private DimensionImpl(Composition c, String name, String symbol) {
            super(name,symbol);
            this.composition = c;
        }

        @Override
        public Composition getComposition() {
            return composition;
        }
        
        @Override
        public final String toString() {
            return "Dimension: " + getName() + " [" + getSymbol() + "]";
        }
    }
    
    //derived dimensions tier 1
    /**
     * <i>a = [LT<sup>-2</sup>]</i>
     */
    public static final Dimension ACCELERATION;
    /**
     * <i>\u03b1 = [T<sup>-2</sup>]</i>
     */
    public static final Dimension ANGULAR_ACCELERATION;
    /**
     * <i>A = [L<sup>2</sup>]</i>
     */
    public static final Dimension AREA;
    /**
     * <i>z = [T<sup>-1</sup>N]</i>
     */
    public static final Dimension CATALYTIC_ACTIVITY;
    /**
     * <i>Q = [TI]</i>
     */
    public static final Dimension ELECTRIC_CHARGE;
    /**
     * <i>f = [T<sup>-1</sup>]</i>
     */
    public static final Dimension FREQUENCY;
    /**
     * <i>q = [ML<sup>-1</sup>]</i>
     */
    public static final Dimension LINEAR_MASS_DENSITY;
    /**
     * <i>\u03d5 = [-]</i>
     */
    public static final Dimension ANGLE;
    /**
     * <i>\u03a9 = [-]</i>
     */
    public static final Dimension SOLID_ANGLE;
    /**
     * <i>\u03b5 = [-]</i>
     */
    public static final Dimension STRAIN;
    /**
     * <i>v = [LT<sup>-1</sup>]</i>
     */
    public static final Dimension VELOCITY;
    /**
     * <i>V = [L<sup>3</sup>]</i>
     */
    public static final Dimension VOLUME;
    
    //derived dimensions tier 2
    /**
     * <i>\u03C9 = [T<sup>-1</sup>]</i>
     */
    public static final Dimension ANGULAR_VELOCITY;
    /**
     * <i>\u03C1<sub>A</sub> = [ML<sup>-2</sup>]</i>
     */
    public static final Dimension AREA_MASS_DENSITY;
    /**
     * <i>F = [MLT<sup>-2</sup>]</i>
     */
    public static final Dimension FORCE;
    /**
     * <i>\u03D5 = [J]</i>
     */
    public static final Dimension LUMINOUS_FLUX;
    /**
     * <i>\u03C1 = [ML<sup>-3</sup>]</i>
     */
    public static final Dimension MASS_DENSITY;
    /**
     * <i>A = [T<sup>-1</sup>]</i>
     */
    public static final Dimension RADIOACTIVITY;
    
    //derived dimensions tier 3
    /**
     * <i>E<sub>V</sub> = [L<sup>-2</sup>J]</i>
     */
    public static final Dimension ILLUMINANCE;
    /**
     * <i>q = [MT<sup>-2</sup>]</i>
     */
    public static final Dimension LINEAR_WEIGHT_DENSITY;
    /**
     * <i>M = [ML<sup>2</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension MOMENT;
    /**
     * <i>p = [ML<sup>-1</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension PRESSURE;
    /**
     * <i>W = [MLT<sup>-2</sup>]</i>
     */
    public static final Dimension WEIGHT;
    /**
     * <i>\u03B3 = [ML<sup>-2</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension WEIGHT_DENSITY;
    
    //derived dimensions tier 4
    /**
     * <i>w = [ML<sup>-1</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension AREA_WEIGHT_DENSITY;
    /**
     * <i>E = [ML<sup>2</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension ENERGY;
    /**
     * <i>\u03C3 = [ML<sup>-1</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension STRESS;
    
    //derived dimensions tier 5
    /**
     * <i>D = [L<sup>2</sup>T<sup>-2</sup>]</i>
     */
    public static final Dimension ABSORBED_DOSE;
    /**
     * <i>P = [ML<sup>2</sup>T<sup>-3</sup>]</i>
     */
    public static final Dimension POWER;
    
    //derived dimensions tier 6
    /**
     * <i>\u03C6 = [ML<sup>2</sup>T<sup>-3</sup>I<sup>-1</sup>]</i>
     */
    public static final Dimension ELECTRIC_POTENTIAL;
    
    //derived dimensions tier 7
    /**
     * <i>C = [M<sup>-1</sup>L<sup>-2</sup>T<sup>4</sup>I<sup>2</sup>]</i>
     */
    public static final Dimension ELECTRIC_CAPACITANCE;
    /**
     * <i>R = [ML<sup>2</sup>T<sup>-3</sup>I<sup>-2</sup>]</i>
     */
    public static final Dimension ELECTRIC_RESISTANCE;
    /**
     * <i>S = [M<sup>-1</sup>L<sup>-2</sup>T<sup>3</sup>I<sup>2</sup>]</i>
     */
    public static final Dimension ELECTRIC_CONDUCTANCE;
    /**
     * <i>\u03A6 = [ML<sup>2</sup>T<sup>-2</sup>I<sup>-1</sup>]</i>
     */
    public static final Dimension MAGNETIC_FLUX;
    
    //derived dimensions tier 8
    /**
     * <i>B = [MT<sup>-2</sup>I<sup>-1</sup>]</i>
     */
    public static final Dimension AREA_MAGNETIC_FLUX_DENSITY;
    /**
     * <i>L = [ML<sup>2</sup>T<sup>-2</sup>I<sup>-2</sup>]</i>
     */
    public static final Dimension INDUCTANCE;
    
    static {
        ACCELERATION            = newDimension().append(LENGTH).append(TIME,-2).withName("ACCELERATION").withSymbol("a").create();
        ANGULAR_ACCELERATION    = newDimension().append(TIME,-2).withName("ANGULAR ACCELERATION").withSymbol("\u03b1").create();
        AREA                    = newDimension().append(LENGTH,2).withName("AREA").withSymbol("A").create();
        CATALYTIC_ACTIVITY      = newDimension().append(AMOUNT_OF_SUBSTANCE).append(TIME,-1).withName("CATALYTIC ACTIVITY").withSymbol("z").create();
        ELECTRIC_CHARGE         = newDimension().append(ELECTRIC_CURRENT).append(TIME).withName("ELECTRIC CHARGE").withSymbol("Q").create();
        FREQUENCY               = newDimension().append(TIME,-1).withName("FREQUENCY").withSymbol("f").create();
        LINEAR_MASS_DENSITY     = newDimension().append(MASS).append(LENGTH,-1).withName("LINEAR MASS DENSITY").withSymbol("q").create();
        ANGLE                   = newDimension().append(DIMENSIONLESS).withName("ANGLE").withSymbol("\u03d5").create();
        SOLID_ANGLE             = newDimension().append(DIMENSIONLESS).withName("SOLID ANGLE").withSymbol("\u03a9").create();
        STRAIN                  = newDimension().append(DIMENSIONLESS).withName("STRAIN").withSymbol("\u03b5").create();
        VELOCITY                = newDimension().append(LENGTH).append(TIME,-1).withName("VELOCITY").withSymbol("v").create();
        VOLUME                  = newDimension().append(LENGTH,3).withName("VOLUME").withSymbol("V").create();
        
        ANGULAR_VELOCITY        = newDimension().append(FREQUENCY).withName("ANGULAR VELOCITY").withSymbol("\u03C9").create();
        AREA_MASS_DENSITY       = newDimension().append(MASS).append(AREA,-1).withName("AREA MASS DENSITY").withSymbol("\u03C1_A").create();
        FORCE                   = newDimension().append(MASS).append(ACCELERATION).withName("FORCE").withSymbol("F").create();
        LUMINOUS_FLUX           = newDimension().append(LUMINOUS_INTENSITY).append(SOLID_ANGLE).withName("LUMINOUS FLUX").withSymbol("\u03D5").create();
        MASS_DENSITY            = newDimension().append(MASS).append(VOLUME,-1).withName("MASS DENSITY").withSymbol("\u03C1").create();
        RADIOACTIVITY           = newDimension().append(FREQUENCY).withName("RADIOACTIVITY").withSymbol("A").create();
        
        ILLUMINANCE             = newDimension().append(LUMINOUS_FLUX).append(AREA,-1).withName("ILLUMINANCE").withSymbol("E_V").create();
        LINEAR_WEIGHT_DENSITY   = newDimension().append(FORCE).append(LENGTH,-1).withName("LINEAR WEIGHT DENSITY").withSymbol("q").create();
        MOMENT                  = newDimension().append(FORCE).append(LENGTH).withName("MOMENT").withSymbol("M").create();
        PRESSURE                = newDimension().append(FORCE).append(AREA,-1).withName("PRESSURE").withSymbol("p").create();
        WEIGHT                  = newDimension().append(FORCE).withName("WEIGHT").withSymbol("W").create();
        WEIGHT_DENSITY          = newDimension().append(FORCE).append(VOLUME,-1).withName("WEIGHT DENSITY").withSymbol("\u03B3").create();
        
        AREA_WEIGHT_DENSITY     = newDimension().append(PRESSURE).withName("AREA WEIGHT DENSITY").withSymbol("w").create();
        ENERGY                  = newDimension().append(MOMENT).withName("ENERGY").withSymbol("E").create();
        STRESS                  = newDimension().append(PRESSURE).withName("STRESS").withSymbol("\u03C3").create();
        
        ABSORBED_DOSE           = newDimension().append(ENERGY).append(MASS,-1).withName("ABSORBED DOSE").withSymbol("D").create();
        POWER                   = newDimension().append(ENERGY).append(TIME,-1).withName("POWER").withSymbol("P").create();
        
        ELECTRIC_POTENTIAL      = newDimension().append(POWER).append(ELECTRIC_CURRENT,-1).withName("ELECTRIC POTENTIAL").withSymbol("\u03C6").create();
        
        ELECTRIC_CAPACITANCE    = newDimension().append(ELECTRIC_CHARGE).append(ELECTRIC_POTENTIAL,-1).withName("ELECTRIC CAPACITANCE").withSymbol("C").create();
        ELECTRIC_RESISTANCE     = newDimension().append(ELECTRIC_POTENTIAL).append(ELECTRIC_CURRENT,-1).withName("ELECTRIC RESISTANCE").withSymbol("R").create();
        ELECTRIC_CONDUCTANCE    = newDimension().append(ELECTRIC_CURRENT).append(ELECTRIC_POTENTIAL,-1).withName("ELECTRIC CONDUCTANCE").withSymbol("S").create();
        MAGNETIC_FLUX           = newDimension().append(ELECTRIC_POTENTIAL).append(TIME).withName("MAGNETIC FLUX").withSymbol("\u03A6").create();
        
        AREA_MAGNETIC_FLUX_DENSITY
                                = newDimension().append(MAGNETIC_FLUX).append(AREA,-1).withName("AREA MAGNETIC FLUX DENSITY").withSymbol("B").create();
        INDUCTANCE              = newDimension().append(MAGNETIC_FLUX).append(ELECTRIC_CURRENT,-1).withName("INDUCTANCE").withSymbol("L").create();
    }
}
