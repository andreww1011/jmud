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
import java.util.Objects;

/**
 * Factory class of common dimensions.
 * 
 * @author andreww1011
 */
public abstract class Dimensions {
    
    private Dimensions(){}
    
    /**
     * Fundamental dimension representing the special null-value dimension.
     * <p>
     * Denoted as <i>[-]</i>.
     */
    public static final FundamentalDimension DIMENSIONLESS = Universe.DIMENSIONLESS;
    
    /**
     * Fundamental dimension of <i>mass</i>, the abstraction of the 
     * notion of a body's resistance to change in motion when a net force
     * is applied.
     * <p>
     * Denoted as <i>[M]</i>.
     */
    public static final FundamentalDimension MASS = Universe.MASS;
    
    /**
     * Fundamental dimension of <i>length</i>, the abstraction of the
     * perception of space.
     * <p>
     * Denoted as <i>[L]</i>.
     */
    public static final FundamentalDimension LENGTH = Universe.LENGTH;
    
    /**
     * Fundamental dimension of <i>time</i>, the abstraction of the perception
     * of sequences of events that occur in irreversible succession from past
     * to present.
     * <p>
     * Denoted as <i>[T]</i>.
     */
    public static final FundamentalDimension TIME = Universe.TIME;
    
    /**
     * Fundamental dimension of <i>electric current</i>, the abstraction of the
     * notion of flow of electric charge.
     * <p>
     * Denoted as <i>[I]</i>.
     */
    public static final FundamentalDimension ELECTRIC_CURRENT = Universe.ELECTRIC_CURRENT;
    
    /**
     * Fundamental dimension of <i>thermodynamic temperature</i>, the 
     * abstraction of the notion of the random vibrations of particle
     * constituents of matter.
     * <p>
     * Denoted as <i>[θ]</i>.
     */
    public static final FundamentalDimension THERMODYNAMIC_TEMPERATURE = Universe.THERMODYNAMIC_TEMPERATURE;
    
    /**
     * Fundamental dimension of <i>amount of substance</i>, the abstraction
     * of the notion of the number of particles comprising matter.
     * <p>
     * Denoted as <i>[N]</i>.
     */
    public static final FundamentalDimension AMOUNT_OF_SUBSTANCE = Universe.AMOUNT_OF_SUBSTANCE;
    
    /**
     * Fundamental dimension of <i>luminous intensity</i>, the abstraction
     * of the notion of light power through space.
     * <p>
     * Denoted as <i>[J]</i>.
     */
    public static final FundamentalDimension LUMINOUS_INTENSITY = Universe.LUMINOUS_INTENSITY;

    static abstract class AbstractComposition implements Composition {
        
        private boolean isHashcodeCalced, isToStringCalced;
        private int hashcode;
        private String toString;
        
        AbstractComposition() {
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
                if (!cc.exponent().isEqualTo(Exponents.ZERO)) {
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
                if (!cc.exponent().isEqualTo(co.getExponent(cc.fundamentalDimension())))
                    return false;
            }
            for (CompositionComponent cc : co) {
                if (!cc.exponent().isEqualTo(this.getExponent(cc.fundamentalDimension())))
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
                FundamentalDimension fd = cc.fundamentalDimension();
                Exponent e = cc.exponent();
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
    
    static abstract class AbstractCompositionComponent implements CompositionComponent {
        
        AbstractCompositionComponent() {}
        
        @Override
        public final boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof CompositionComponent))
                return false;
            CompositionComponent c = (CompositionComponent)o;
            return this.fundamentalDimension().equals(c.fundamentalDimension())
                    && this.exponent().isEqualTo(c.exponent());
        }
        
        private static final int i = 5, j = 23; //magic number
        
        @Override
        public final int hashCode() {
            int hash = i;
            hash = j * hash + Objects.hashCode(fundamentalDimension());
            hash = j * hash + Objects.hashCode(exponent());
            return hash;
        }
        
        @Override
        public final String toString() {
            return new StringBuilder()
                .append("Fundamental Dimension: ")
                .append(fundamentalDimension().getName())
                .append(" (")
                .append(fundamentalDimension().getSymbol())
                .append(") , ")
                .append("Exponent: ")
                .append(exponent().toString())
                .toString();
        }
    }
    
    static abstract class AbstractDimension implements Dimension {

        private final String name, symbol;
        
        AbstractDimension(String name, String symbol) {
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
    
    /**
     * Returns a builder for creating a new dimension.
     */
    public static final DimensionBuilder newDimension() {
        return DimensionBuilder.NULL_BUILDER;
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
        public FundamentalDimension fundamentalDimension() {
            return fd;
        }

        @Override
        public Exponent exponent() {
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
     * Dimension of <i>acceleration</i>, the rate of change of the {@linkplain #VELOCITY velocity}
     * of an object with respect to {@linkplain #TIME time}.
     * 
     * <p>Denoted as <i>[a]</i>, with composition <i>[{@linkplain #LENGTH L}{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension ACCELERATION;
    
    /**
     * Dimension of <i>angular acceleration</i>, the rate of change of the {@linkplain #ANGULAR_VELOCITY angular velocity}
     * of an object with respect to {@linkplain #TIME time}.
     * <p>Denoted as <i>[\u03b1]</i>, with composition <i>[{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension ANGULAR_ACCELERATION;
    
    /**
     * Dimension of <i>area</i>, the extent of a region on a plane.
     * 
     * <p>Denoted as <i>[A]</i>, with composition <i>[{@linkplain #LENGTH L}<sup>2</sup>]</i>.
     */
    public static final Dimension AREA;
    
    /**
     * Dimension of <i>catalytic activity</i>, the rate of change of a chemical 
     * reaction caused by the presence of a catalyst.
     * 
     * <p>Denoted as <i>[z]</i>, with composition <i>[{@linkplain #TIME T}<sup>-1</sup>{@linkplain #AMOUNT_OF_SUBSTANCE N}]</i>.
     */
    public static final Dimension CATALYTIC_ACTIVITY;
    
    /**
     * Dimension of <i>electric charge</i>, the property of charged matter to 
     * experience a force when placed in an electromagnetic field.
     * 
     * <p>Denoted as <i>[q]</i>, with composition <i>[{@linkplain #TIME T}{@linkplain #ELECTRIC_CURRENT I}]</i>.
     */
    public static final Dimension ELECTRIC_CHARGE;
    
    /**
     * Dimension of <i>frequency</i>, the number of occurrences of a repeated
     * event per unit of time.
     * 
     * <p>Denoted as <i>[f]</i>, with composition <i>[{@linkplain #TIME T}<sup>-1</sup>]</i>.
     */
    public static final Dimension FREQUENCY;
    
    /**
     * Dimension of <i>linear mass density</i>, the amount of mass per unit of length.
     * 
     * <p>Denoted as <i>[\u03C1<sub>l</sub>]</i>, with composition <i>[{@linkplain #MASS M}{@linkplain #LENGTH L}<sup>-1</sup>]</i>.
     */
    public static final Dimension LINEAR_MASS_DENSITY;
    
    /**
     * Dimension of <i>angle</i>, the figure formed by two rays sharing a common
     * endpoint.
     * 
     * <p>Denoted as <i>[\u03d5]</i>, with composition <i>[{@linkplain #DIMENSIONLESS -}]</i>.
     */
    public static final Dimension ANGLE;
    
    /**
     * Dimension of <i>solid angle</i>, the amount of the field of view a 
     * particular point that an object covers.
     * 
     * <p>Denoted as <i>[\u03a9]</i>, with composition <i>[{@linkplain #DIMENSIONLESS -}]</i>.
     */
    public static final Dimension SOLID_ANGLE;
    
    /**
     * Dimension of <i>strain</i>, the deformation of a body relative to some
     * reference configuration.
     * 
     * <p>Denoted as <i>[\u03b5]</i>, with composition <i>[{@linkplain #DIMENSIONLESS -}]</i>.
     */
    public static final Dimension STRAIN;
    
    /**
     * Dimension of <i>velocity</i>, the rate of change of position per unit of time.
     * 
     * <p>Denoted as <i>[v]</i>, with composition <i>[{@linkplain #LENGTH L}{@linkplain #TIME T}<sup>-1</sup>]</i>.
     */
    public static final Dimension VELOCITY;
    
    /**
     * Dimension of <i>volume</i>, the extent of a body in space.
     * 
     * <p>Denoted as <i>[V]</i>, with composition <i>[{@linkplain #LENGTH L}<sup>3</sup>]</i>.
     */
    public static final Dimension VOLUME;
    
    //derived dimensions tier 2
    /**
     * Dimension of <i>angular velocity</i>, the rate of change of angular position
     * with respect to time.
     * 
     * <p>Denoted as <i>[\u03C9]</i>, with composition <i>[{@linkplain #TIME T}<sup>-1</sup>]</i>.
     */
    public static final Dimension ANGULAR_VELOCITY;
    
    /**
     * Dimension of <i>area mass density</i>, the amount of mass per unit of area.
     * 
     * <p>Denoted as <i>[\u03C1<sub>A</sub>]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>-2</sup>]</i>.
     */
    public static final Dimension AREA_MASS_DENSITY;
    
    /**
     * Dimension of <i>force</i>, an influence that changes the motion of an object.
     * 
     * <p>Denoted as <i>[F]</i>, with composition <i>[{@linkplain #MASS M}{@linkplain
     * #LENGTH L}{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension FORCE;
    
    /**
     * Dimension of <i>luminous flux</i>, the amount of light emitted by a source
     * per unit of time.
     * 
     * <p>Denoted as <i>[\u03D5<sub>v</sub>]</i>, with composition <i>[{@linkplain #LUMINOUS_INTENSITY J}]</i>.
     */
    public static final Dimension LUMINOUS_FLUX;
    
    /**
     * Dimension of <i>mass density</i>, the amount of mass per unit of volume.
     * 
     * <p>Denoted as <i>[\u03C1]</i>, with composition <i>[{@linkplain #MASS M}{@linkplain 
     * #LENGTH L}<sup>-3</sup>]</i>.
     */
    public static final Dimension MASS_DENSITY;
    
    /**
     * Dimension of <i>radioactivity</i>, the rate of nuclear decay with respect 
     * to time.
     * 
     * <p>Denoted as <i>[A]</i>, with composition <i>[{@linkplain #TIME T}<sup>-1</sup>]</i>.
     */
    public static final Dimension RADIOACTIVITY;
    
    //derived dimensions tier 3
    /**
     * Dimension of <i>illuminance</i>, the luminous flux incident on a surface
     * per unit area.
     * 
     * <p>Denoted as <i>[E<sub>V</sub>]</i>, with composition <i>[{@linkplain 
     * #LENGTH L}<sup>-2</sup>{@linkplain #LUMINOUS_INTENSITY J}]</i>.
     */
    public static final Dimension ILLUMINANCE;
    
    /**
     * Dimension of <i>linear weight density</i>, the amount of weight per unit of length.
     * 
     * <p>Denoted as <i>[q]</i>, with composition <i>[{@linkplain #MASS M}{@linkplain 
     * #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension LINEAR_WEIGHT_DENSITY;
    
    /**
     * Dimension of <i>moment</i>, the amount of rotational force.
     * 
     * <p>Denoted as <i>[M]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension MOMENT;
    
    /**
     * Dimension of <i>pressure</i>, the amount of force applied perpendicular 
     * to a surface per unit area.
     * 
     * <p>Denoted as <i>[p]</i>, with composition <i>[{@linkplain #MASS
     * M}{@linkplain #LENGTH L}<sup>-1</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension PRESSURE;
    
    /**
     * Dimension of <i>weight</i>, the force acting on an object due to gravity.
     * 
     * <p>Denoted as <i>[W]</i>, with composition <i>[{@linkplain #MASS M}{@linkplain 
     * #LENGTH L}{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension WEIGHT;
    
    /**
     * Dimension of <i>weight density</i>, the force acting on an object due
     * to gravity per unit of volume it occupies.
     * 
     * <p>Denoted as <i>[\u03B3]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>-2</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension WEIGHT_DENSITY;
    
    //derived dimensions tier 4
    /**
     * Dimension of <i>area weight density</i>, the amount of mass per unit of area.
     * 
     * <p>Denoted as <i>[w]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>-1</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension AREA_WEIGHT_DENSITY;
    
    /**
     * Dimension of <i>energy</i>, the ability to exert a force causing displacement
     * of an object.
     * 
     * <p>Denoted as <i>[E]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension ENERGY;
    
    /**
     * Dimension of <i>stress</i>, the internal resistance exhibited by a body subject to an
     * external force.
     * 
     * <p>Denoted as <i>[\u03C3]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>-1</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension STRESS;
    
    //derived dimensions tier 5
    /**
     * Dimension of <i>absorbed does</i>, the energy deposited in matter by
     * ionizing radiation per unit mass.
     * 
     * <p>Denoted as <i>[D]</i>, with composition <i>[{@linkplain #LENGTH 
     * L}<sup>2</sup>{@linkplain #TIME T}<sup>-2</sup>]</i>.
     */
    public static final Dimension ABSORBED_DOSE;
    
    /**
     * Dimension of <i>power</i>, the amount of energy produced or consumed per
     * unit of time.
     * 
     * <p>Denoted as <i>[P]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME T}<sup>-3</sup>]</i>.
     */
    public static final Dimension POWER;
    
    //derived dimensions tier 6
    /**
     * Dimension of <i>electric potential</i>, the amount of energy needed to move
     * a unit of electric charge in an electric field.
     * 
     * <p>Denoted as <i>[\u03D5]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME 
     * T}<sup>-3</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>-1</sup>]</i>.
     */
    public static final Dimension ELECTRIC_POTENTIAL;
    
    //derived dimensions tier 7
    /**
     * Dimension of <i>electric capacitance</i>, the amount of electric charge
     * per unit of electric potential.
     * 
     * <p>Denoted as <i>[C]</i>, with composition <i>[{@linkplain #MASS 
     * M}<sup>-1</sup>{@linkplain #LENGTH L}<sup>-2</sup>{@linkplain #TIME
     * T}<sup>4</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>2</sup>]</i>.
     */
    public static final Dimension ELECTRIC_CAPACITANCE;
    
    /**
     * Dimension of <i>electric resistance</i>, the opposition to the flow of electric current.
     * 
     * <p>Denoted as <i>[R]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME
     * T}<sup>-3</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>-2</sup>]</i>.
     */
    public static final Dimension ELECTRIC_RESISTANCE;
    
    /**
     * Dimension of <i>electric conductance</i>, the ease of flow of
     * electric current.
     * 
     * <p>Denoted as <i>[S]</i>, with composition <i>[{@linkplain #MASS
     * M}<sup>-1</sup>{@linkplain #LENGTH L}<sup>-2</sup>{@linkplain #TIME
     * T}<sup>3</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>2</sup>]</i>.
     */
    public static final Dimension ELECTRIC_CONDUCTANCE;
    
    /**
     * Dimension of <i>magnetic flux</i>, the magnitude of a magnetic field through
     * a surface.
     * 
     * <p>Denoted as <i>[\u03A6]</i>, with composition <i>[{@linkplain #MASS
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME
     * T}<sup>-2</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>-1</sup>]</i>.
     */
    public static final Dimension MAGNETIC_FLUX;
    
    //derived dimensions tier 8
    /**
     * Dimension of <i>area magnetic flux density</i>, the amount of
     * magnetic flux per unit of area.
     * 
     * <p>Denoted as <i>[B]</i>, with composition <i>[{@linkplain #MASS 
     * M}{@linkplain #TIME T}<sup>-2</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>-1</sup>]</i>.
     */
    public static final Dimension AREA_MAGNETIC_FLUX_DENSITY;
    
    /**
     * Dimension of <i>inductance</i>, the tendency of an electrical conductor to oppose 
     * a change in the electric current flowing through it.
     * 
     * <p>Denoted as <i>[L]</i>, with composition <i>[{@linkplain #MASS
     * M}{@linkplain #LENGTH L}<sup>2</sup>{@linkplain #TIME
     * T}<sup>-2</sup>{@linkplain #ELECTRIC_CURRENT I}<sup>-2</sup>]</i>.
     */
    public static final Dimension INDUCTANCE;
    
    static {
        ACCELERATION            = newDimension().append(LENGTH).append(TIME,-2).withName("ACCELERATION").withSymbol("a").create();
        ANGULAR_ACCELERATION    = newDimension().append(TIME,-2).withName("ANGULAR ACCELERATION").withSymbol("\u03b1").create();
        AREA                    = newDimension().append(LENGTH,2).withName("AREA").withSymbol("A").create();
        CATALYTIC_ACTIVITY      = newDimension().append(AMOUNT_OF_SUBSTANCE).append(TIME,-1).withName("CATALYTIC ACTIVITY").withSymbol("z").create();
        ELECTRIC_CHARGE         = newDimension().append(ELECTRIC_CURRENT).append(TIME).withName("ELECTRIC CHARGE").withSymbol("q").create();
        FREQUENCY               = newDimension().append(TIME,-1).withName("FREQUENCY").withSymbol("f").create();
        LINEAR_MASS_DENSITY     = newDimension().append(MASS).append(LENGTH,-1).withName("LINEAR MASS DENSITY").withSymbol("\u03C1_l").create();
        ANGLE                   = newDimension().append(DIMENSIONLESS).withName("ANGLE").withSymbol("\u03d5").create();
        SOLID_ANGLE             = newDimension().append(DIMENSIONLESS).withName("SOLID ANGLE").withSymbol("\u03a9").create();
        STRAIN                  = newDimension().append(DIMENSIONLESS).withName("STRAIN").withSymbol("\u03b5").create();
        VELOCITY                = newDimension().append(LENGTH).append(TIME,-1).withName("VELOCITY").withSymbol("v").create();
        VOLUME                  = newDimension().append(LENGTH,3).withName("VOLUME").withSymbol("V").create();
        
        ANGULAR_VELOCITY        = newDimension().append(FREQUENCY).withName("ANGULAR VELOCITY").withSymbol("\u03C9").create();
        AREA_MASS_DENSITY       = newDimension().append(MASS).append(AREA,-1).withName("AREA MASS DENSITY").withSymbol("\u03C1_A").create();
        FORCE                   = newDimension().append(MASS).append(ACCELERATION).withName("FORCE").withSymbol("F").create();
        LUMINOUS_FLUX           = newDimension().append(LUMINOUS_INTENSITY).append(SOLID_ANGLE).withName("LUMINOUS FLUX").withSymbol("\u03D5_v").create();
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
        
        ELECTRIC_POTENTIAL      = newDimension().append(POWER).append(ELECTRIC_CURRENT,-1).withName("ELECTRIC POTENTIAL").withSymbol("\u03D5").create();
        
        ELECTRIC_CAPACITANCE    = newDimension().append(ELECTRIC_CHARGE).append(ELECTRIC_POTENTIAL,-1).withName("ELECTRIC CAPACITANCE").withSymbol("C").create();
        ELECTRIC_RESISTANCE     = newDimension().append(ELECTRIC_POTENTIAL).append(ELECTRIC_CURRENT,-1).withName("ELECTRIC RESISTANCE").withSymbol("R").create();
        ELECTRIC_CONDUCTANCE    = newDimension().append(ELECTRIC_CURRENT).append(ELECTRIC_POTENTIAL,-1).withName("ELECTRIC CONDUCTANCE").withSymbol("S").create();
        MAGNETIC_FLUX           = newDimension().append(ELECTRIC_POTENTIAL).append(TIME).withName("MAGNETIC FLUX").withSymbol("\u03A6").create();
        
        AREA_MAGNETIC_FLUX_DENSITY
                                = newDimension().append(MAGNETIC_FLUX).append(AREA,-1).withName("AREA MAGNETIC FLUX DENSITY").withSymbol("B").create();
        INDUCTANCE              = newDimension().append(MAGNETIC_FLUX).append(ELECTRIC_CURRENT,-1).withName("INDUCTANCE").withSymbol("L").create();
    }
    
    /**
     * Builder class for creating new dimensions.
     * 
     * <p>A dimension is built using this builder by the following steps:
     * <ol>
     * <li>Use {@link DimensionBuilder#append(com.jamw.jmud.Dimension) append()} 
     * one or more times to append an existing {@link Dimension dimension} 
     * or {@link FundamentalDimension fundamental dimension}, optionally raised 
     * to a rational number {@link Exponent exponent}, to the special null 
     * dimension {@link Universe#getDimensionless() "dimensionless"}. </li>
     * <li>Optionally, provide a descriptive name with 
     * {@link DimensionBuilder#withName(java.lang.String) withName()}. If omitted,
     * a descriptive name will attempt to be
     * generated based on the composition of the dimension.</li>
     * <li>Optionally, provide a descriptive symbol with 
     * {@link DimensionBuilder#withSymbol(java.lang.String) withSymbol()}. If omitted,
     * a descriptive symbol will attempt to be
     * generated based on the composition of the dimension.</li>
     * <li>Create the dimension using {@link DimensionBuilder#create() create()}.
     * </ol>
     * The methods of this class return instances of this class for method chaining.
     * <p>Instances of this class, including those returned by methods,
     * are immutable and thread safe.  
     * 
     * @author andreww1011
     */
    public static final class DimensionBuilder {

        private static final DimensionBuilder NULL_BUILDER = new DimensionBuilder("","",Map.of());
        
        private final String name, symbol;
        private final Map<FundamentalDimension,Exponent> compositionMap;
        
        private DimensionBuilder(String name, String symbol, Map<FundamentalDimension,Exponent> compositionMap) {
            this.name = name;
            this.symbol = symbol;
            this.compositionMap = compositionMap;
        }
        
        /**
         * Appends the specified dimension raised to the rational exponent one to
         * this builder.
         * @param d dimension to append.
         * @return a DimensionBuilder instance with the specified dimension appended to it.
         * @see DimensionBuilder#append(Dimension, Exponent) 
         */
        public DimensionBuilder append(Dimension d) {
            return append(d,1); //magic number
        }

        /**
         * Appends the specified dimension raised to the specified integer power to
         * this builder.
         * @param d dimension to append.
         * @param power integer power of the specified dimension to be appended.
         * @return a DimensionBuilder instance with the specified dimension and power appended to it.
         * @see DimensionBuilder#append(Dimension, Exponent) 
         */
        public DimensionBuilder append(Dimension d,int power) {
            return append(d,power,1); //magic number
        }
        
        /**
         * Appends the specified dimension raised to the rational number power
         * <code>exponentNumerator/exponentDenominator</code> to this builder.
         * @param d dimension to append.
         * @param exponentNumerator integer numerator of the rational number power of the specified dimension to be appended.
         * @param exponentDenominator integer denominator of the rational number power of the specified dimension to be appended.  Must not be zero.
         * @return a DimensionBuilder instance with the specified dimension and rational number power appended to it.
         * @throws IllegalArgumentException if the denominator of the rational number is zero.
         * @see DimensionBuilder#append(Dimension, Exponent) 
         */
        public DimensionBuilder append(Dimension d,int exponentNumerator, int exponentDenominator) throws IllegalArgumentException{
            return append(d,Exponents.of(exponentNumerator,exponentDenominator));
        }

        /**
         * Appends the specified dimension raised to the rational number exponent
         * to this builder.
         * @param d dimension to append.
         * @param e exponent of the specified dimension to be appended.
         * @return a DimensionBuilder instance with the specified dimension and exponent appended to it.
         */
        public DimensionBuilder append(Dimension d,Exponent e) {
            Map<FundamentalDimension,Exponent> map = new HashMap<>(compositionMap);
            
            Exponent dPow;
            FundamentalDimension fd;
            Exponent fe;
            for (CompositionComponent c : d.getComposition()) {
                fd = c.fundamentalDimension();
                if (fd.equals(Dimensions.DIMENSIONLESS)) 
                    continue;
                fe = c.exponent();
                dPow = Exponents.power(fe, e);
                if (map.containsKey(fd))
                    map.put(fd, Exponents.product(map.get(fd), dPow));
                else
                    map.put(fd, dPow);
            }
            return new DimensionBuilder(name,symbol,map);
        }

        /**
         * Reserves the specified string as the name of the dimension to be 
         * created by this builder.  Subsequent calls to this method overwrites
         * any previously specified names.
         * @param name canonical name of dimension.
         * @return a DimensionBuilder instance incorporating the specified name.
         */
        public DimensionBuilder withName(String name) {
            return new DimensionBuilder(name,symbol,compositionMap);   
        }

        /**
         * Reserves the specified string as the symbol of the dimension to be 
         * created by this builder.  Subsequent calls to this method overwrites
         * any previously specified symbols.
         * @param symbol canonical symbol of dimension.
         * @return a DimensionBuilder instance incorporating the specified symbol.
         */
        public DimensionBuilder withSymbol(String symbol) {
            return new DimensionBuilder(name,symbol,compositionMap);   
        }

        /**
         * Returns a new Dimension object using the 
         * parameters set by this builder.
         */
        public Dimension create() {
            Map<FundamentalDimension,Exponent> map = new HashMap<>(compositionMap);
            cleanCompositionMap(map);
            Composition composition = new CompositionImpl(map);
            String n = formatName(name,composition);
            String s = formatSymbol(symbol,composition);
            return new DimensionImpl(composition,n,s);
        }
        
        private static void cleanCompositionMap(Map<FundamentalDimension,Exponent> map) {
            map.entrySet().removeIf((e) -> e.getValue().equals(Exponents.ZERO));
            if (map.isEmpty())
                map.put(Dimensions.DIMENSIONLESS, Exponents.ONE);
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
                FundamentalDimension fd = cc.fundamentalDimension();
                Exponent e = cc.exponent();
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
}
