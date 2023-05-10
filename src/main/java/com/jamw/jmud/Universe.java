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

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Factory class for creating fundamental dimensions and fundamental units.
 * 
 * @author andreww1011
 */
public abstract class Universe {
    
    static final FundamentalDimension DIMENSIONLESS,
                                      MASS,
                                      LENGTH,
                                      TIME,
                                      ELECTRIC_CURRENT,
                                      THERMODYNAMIC_TEMPERATURE,
                                      AMOUNT_OF_SUBSTANCE,
                                      LUMINOUS_INTENSITY;
    static final FundamentalUnit UNITLESS,
                                 KILOGRAM,
                                 METER,
                                 SECOND,
                                 AMPERE,
                                 KELVIN,
                                 MOLE,
                                 CANDELA;
    
    static {
        DIMENSIONLESS               = newFundamentalPair("DIMENSIONLESS","-","UNITLESS","-")
                                      .getFundamentalDimension();
        MASS                        = newFundamentalPair("MASS","M","KILOGRAM","kg")
                                      .getFundamentalDimension();
        LENGTH                      = newFundamentalPair("LENGTH","L","METER","m")
                                      .getFundamentalDimension();
        TIME                        = newFundamentalPair("TIME","T","SECOND","s")
                                      .getFundamentalDimension();
        ELECTRIC_CURRENT            = newFundamentalPair("ELECTRIC CURRENT","I","AMPERE","A")
                                      .getFundamentalDimension();
        THERMODYNAMIC_TEMPERATURE   = newFundamentalPair("THERMODYNAMIC TEMPERATURE","θ","KELVIN","K")
                                      .getFundamentalDimension();
        AMOUNT_OF_SUBSTANCE         = newFundamentalPair("AMOUNT OF SUBSTANCE","N","MOLE","mol")
                                      .getFundamentalDimension();
        LUMINOUS_INTENSITY          = newFundamentalPair("LUMINOUS INTENSITY","J","CANDELA","cd")
                                      .getFundamentalDimension();
        UNITLESS        = DIMENSIONLESS.getFundamentalUnit();
        KILOGRAM        = MASS.getFundamentalUnit();
        METER           = LENGTH.getFundamentalUnit();
        SECOND          = TIME.getFundamentalUnit();
        AMPERE          = ELECTRIC_CURRENT.getFundamentalUnit();
        KELVIN          = THERMODYNAMIC_TEMPERATURE.getFundamentalUnit();
        MOLE            = AMOUNT_OF_SUBSTANCE.getFundamentalUnit();
        CANDELA         = LUMINOUS_INTENSITY.getFundamentalUnit();
    }
    
    /**
     * Returns the special fundamental dimension corresponding to dimensionless 
     * measurements.
     * 
     * <p>An operation on dimensions that results in the null dimension (i.e. a dimension
     * whose composition comprises all fundamental dimensions raised to the power
     * zero) returns this object.  The composition of the 
     * {@code Dimensionless} dimension is always itself raised to the power one.
     * An operation on a dimensionless measurement that results in a
     * non-dimensionless measurement will return a dimension whose composition
     * always excludes the {@code Dimensionless} fundamental dimension.  The name 
     * and symbol of the {@code Dimensionless} dimension are <code>"DIMENSIONLESS"</code> 
     * and <code>"-"</code>, respectively.
     *
     * @return a {@link com.jamw.jmud.FundamentalDimension} object
     */
    public static final FundamentalDimension getDimensionless() {
        return DIMENSIONLESS;
    }
    
    /**
     * Returns the special fundamental unit corresponding to dimensionless 
     * measurements.  
     * 
     * <p>The fundamental unit paired to the <code>Unitless</code> unit 
     * is {@link com.jamw.jmud.Universe#getDimensionless()}.  The name
     * and symbol of the {@code Unitless} unit are <code>"UNITLESS"</code> 
     * and <code>"-"</code>, respectively.
     *
     * @return a {@link com.jamw.jmud.FundamentalUnit} object
     */
    public static final FundamentalUnit getUnitless() {
        return UNITLESS;
    }
    
    private Universe() {}
    
    /**
     * Returns a fundamental pair for the specified identifiers.  The specified
     * names and symbols for the fundamental dimension and fundamental unit cannot
     * be <code>null</code> nor blank.  
     * 
     * <p>This is the mechanism by which new fundamental dimensions and corresponding
     * fundamental units are created.
     * 
     * @param FundamentalDimensionName name for the fundamental dimension.
     * @param FundamentalDimensionSymbol symbol for the fundamental dimension.
     * @param FundamentalUnitName name for the fundamental unit.
     * @param FundamentalUnitSymbol name for the fundamental unit.
     * @return a fundamental pair containing a new fundamental dimension
     *         and fundamental unit with the specified names and symbols.
     */
    public static final FundamentalPair newFundamentalPair(String FundamentalDimensionName,
                                                    String FundamentalDimensionSymbol,
                                                    String FundamentalUnitName,
                                                    String FundamentalUnitSymbol) {
        checkString(FundamentalDimensionName);
        checkString(FundamentalDimensionSymbol);
        checkString(FundamentalUnitName);
        checkString(FundamentalUnitSymbol);
        return new FundamentalPairImpl(
                FundamentalDimensionName, 
                FundamentalDimensionSymbol,
                FundamentalUnitName,
                FundamentalUnitSymbol);
    }
    
    private static void checkString(String s) {
        Objects.requireNonNull(s);
        if (s.isBlank())
            throw new IllegalArgumentException();
    }
    
    private static final class FundamentalPairImpl implements FundamentalPair {
        
        private final FundamentalDimension fundamentalDimension;
        private final FundamentalUnit fundamentalUnit;
        
        private FundamentalPairImpl(String fundamentalDimensionName,
                                    String fundamentalDimensionSymbol,
                                    String fundamentalUnitName,
                                    String fundamentalUnitSymbol) {
            fundamentalDimension = new FundamentalDimensionImpl(
                                    fundamentalDimensionName,
                                    fundamentalDimensionSymbol,
                                    fundamentalUnitName,
                                    fundamentalUnitSymbol);
            fundamentalUnit = fundamentalDimension.getFundamentalUnit();
        }
        
        @Override
        public final FundamentalDimension getFundamentalDimension() {
            return fundamentalDimension;
        }
        
        @Override
        public final FundamentalUnit getFundamentalUnit() {
            return fundamentalUnit;
        } 
    }
    
    private static final class FundamentalDimensionImpl 
            extends Dimensions.AbstractDimension
            implements FundamentalDimension {
        
        private final Composition composition;
        private final FundamentalUnit fundamentalUnit;
        
        private FundamentalDimensionImpl(String fundamentalDimensionName,
                                         String fundamentalDimensionSymbol,
                                         String fundamentalUnitName,
                                         String fundamentalUnitSymbol) {
            super(fundamentalDimensionName,fundamentalDimensionSymbol);
            composition = new FundamentalDimensionCompositionImpl();
            fundamentalUnit = new FundamentalUnitImpl(this,fundamentalUnitName,fundamentalUnitSymbol);
        }
        
        private final class FundamentalDimensionCompositionImpl extends Dimensions.AbstractComposition {
            
            private final CompositionComponent cc;
            
            private FundamentalDimensionCompositionImpl() {
                super();
                cc = new FundamentalDimensionCompositionComponentImpl();
            }
            
            private final class FundamentalDimensionCompositionComponentImpl extends Dimensions.AbstractCompositionComponent {
                @Override
                public FundamentalDimension fundamentalDimension() {
                    return FundamentalDimensionImpl.this;
                }
                @Override
                public Exponent exponent() {
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
        public final Composition getComposition() {
            return composition;
        }
        
        @Override
        public final FundamentalUnit getFundamentalUnit() {
            return fundamentalUnit;
        }
        
        @Override
        public final String toString() {
            return "Fundamental Dimension: " + getName() + " [" + getSymbol() + "]";
        }
    }
    
    private static final class FundamentalUnitImpl 
            extends Units.AbstractUnit
            implements FundamentalUnit {
        
        private final FundamentalDimension fd;

        private FundamentalUnitImpl(FundamentalDimension fd,
                                    String name,
                                    String symbol) {
            super(name,symbol);
            this.fd = fd;
        }

        @Override
        public FundamentalDimension getDimension() {
            return fd;
        }
        
        @Override
        public final String toString() {
            return "Fundamental Unit: " + getName() + " (" + getSymbol() + ")";
        }
        
        @Override
        public final Scalar getScale() {
            return Expressions.ONE;
        }
    }
}
