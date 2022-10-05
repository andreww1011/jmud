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

import com.jamw.jmud.fields.DoubleField;
import org.junit.jupiter.api.Nested;

/**
 *
 * @author andreww1011
 */
public class FundamentalUnitsTest {
    
    private static final FundamentalUnit OTHER_FUNDAMENTAL_UNIT =
    Universe.newFundamentalPair("DummyName", "DummySymbol", "DummyUnitName", "DummyUnitSymbol").getFundamentalUnit();
    
    private static abstract class FundamentalUnitTest implements FundamentalUnitContract {

        private final String expectedName, expectedSymbol;
        private final FundamentalUnit theUnit;
        private final FundamentalDimension expectedDimension;
        
        FundamentalUnitTest(FundamentalUnit theUnit,
                            FundamentalDimension expectedDimension,
                            String expectedName, 
                            String expectedSymbol) {
            this.expectedName = expectedName;
            this.expectedSymbol = expectedSymbol;
            this.theUnit = theUnit;
            this.expectedDimension = expectedDimension;
        }
        
        @Override
        public FundamentalUnit getFundamentalUnit() {
            return theUnit;
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
        public FundamentalUnit createDifferentObject() {
            return OTHER_FUNDAMENTAL_UNIT;
        }

        @Override
        public Field.Factory<?> getFieldFactory() {
            return DoubleField.factory(); //TODO implement independent factory and field.
        }

        @Override
        public FundamentalDimension expectedFundamentalDimension() {
            return expectedDimension;
        }
    }
    
    @Nested
    public class UnitlessTest extends FundamentalUnitTest {
        
        public UnitlessTest() {
            super(Units.UNITLESS,
                  Dimensions.DIMENSIONLESS,
                  "UNITLESS",
                  "-");
        }
    }
    
    @Nested
    public class MeterTest extends FundamentalUnitTest {
        
        public MeterTest() {
            super(Units.METER,
                  Dimensions.LENGTH,
                  "METER",
                  "m");
        }
    }
    
    @Nested
    public class KilogramTest extends FundamentalUnitTest {
        
        public KilogramTest() {
            super(Units.KILOGRAM,
                  Dimensions.MASS,
                  "KILOGRAM",
                  "kg");
        }
    }
    
    @Nested
    public class SecondTest extends FundamentalUnitTest {
        
        public SecondTest() {
            super(Units.SECOND,
                  Dimensions.TIME,
                  "SECOND",
                  "s");
        }
    }
    
    @Nested
    public class AmpereTest extends FundamentalUnitTest {
        
        public AmpereTest() {
            super(Units.AMPERE,
                  Dimensions.ELECTRIC_CURRENT,
                  "AMPERE",
                  "A");
        }
    }
    
    @Nested
    public class KelvinTest extends FundamentalUnitTest {
        
        public KelvinTest() {
            super(Units.KELVIN,
                  Dimensions.THERMODYNAMIC_TEMPERATURE,
                  "KELVIN",
                  "K");
        }
    }
    
    @Nested
    public class MoleTest extends FundamentalUnitTest {
        
        public MoleTest() {
            super(Units.MOLE,
                  Dimensions.AMOUNT_OF_SUBSTANCE,
                  "MOLE",
                  "mol");
        }
    }
    
    @Nested
    public class CandelaTest extends FundamentalUnitTest {
        
        public CandelaTest() {
            super(Units.CANDELA,
                  Dimensions.LUMINOUS_INTENSITY,
                  "CANDELA",
                  "cd");
        }
    }
}