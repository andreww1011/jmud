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

import org.junit.jupiter.api.Nested;

/**
 *
 * @author andreww1011
 */
public class FundamentalDimensionsTest {
    
    private static final FundamentalDimension OTHER_FUNDAMENTAL_DIMENSION =
    Universe.newFundamentalPair("DummyName", "DummySymbol", "DummyUnitName", "DummyUnitSymbol").getFundamentalDimension();
    
    private static abstract class FundamentalDimensionTest implements FundamentalDimensionContract {

        private final String expectedName, expectedSymbol;
        private final FundamentalDimension theDimension;
        
        FundamentalDimensionTest(FundamentalDimension thisDimension,
                                 String expectedName, 
                                 String expectedSymbol) {
            this.expectedName = expectedName;
            this.expectedSymbol = expectedSymbol;
            this.theDimension = thisDimension;
        }
        
        @Override
        public FundamentalDimension getFundamentalDimension() {
            return theDimension;
        }

        @Override
        public String expectedName() {
            return expectedName;
        }

        @Override
        public String expectedSymbol() {
            return expectedSymbol;
        }

        @Override
        public FundamentalDimension createDifferentObject() {
            return OTHER_FUNDAMENTAL_DIMENSION;
        }
    }
    
    @Nested
    public class DimensionlessTest extends FundamentalDimensionTest {
        
        public DimensionlessTest() {
            super(Dimensions.DIMENSIONLESS,
                  "DIMENSIONLESS",
                  "-");
        }
    }
    
    @Nested
    public class MassTest extends FundamentalDimensionTest {
        
        public MassTest() {
            super(Dimensions.MASS,
                  "MASS",
                  "M");
        }
    }
    
    @Nested
    public class LengthTest extends FundamentalDimensionTest {
        
        public LengthTest() {
            super(Dimensions.LENGTH,
                  "LENGTH",
                  "L");
        }
    }
    
    @Nested
    public class TimeTest extends FundamentalDimensionTest {
        
        public TimeTest() {
            super(Dimensions.TIME,
                  "TIME",
                  "T");
        }
    }

    @Nested
    public class ElectricCurrentTest extends FundamentalDimensionTest {
        
        public ElectricCurrentTest() {
            super(Dimensions.ELECTRIC_CURRENT,
                  "ELECTRIC CURRENT",
                  "I");
        }
    }
    
    @Nested
    public class ThermodynamicTemperatureTest extends FundamentalDimensionTest {
        
        public ThermodynamicTemperatureTest() {
            super(Dimensions.THERMODYNAMIC_TEMPERATURE,
                  "THERMODYNAMIC TEMPERATURE",
                  "θ");
        }
    }
    
    @Nested
    public class AmountOfSubstanceTest extends FundamentalDimensionTest {
        
        public AmountOfSubstanceTest() {
            super(Dimensions.AMOUNT_OF_SUBSTANCE,
                  "AMOUNT OF SUBSTANCE",
                  "N");
        }
    }
    
    @Nested
    public class LuminousIntensityTest extends FundamentalDimensionTest {
        
        public LuminousIntensityTest() {
            super(Dimensions.LUMINOUS_INTENSITY,
                  "LUMINOUS INTENSITY",
                  "J");
        }
    }
}