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

/**
 * A unit is a reference measurement of a particular dimension.  
 * The magnitude of a measurement can be represented as a linear scale factor of 
 * any unit with commensurable dimension, <i>x \u21A6 ax</i>, where <i>a</i> is 
 * a positive number.
 * 
 * <p>A {@link FundamentalUnit fundamental unit} is a reference measurement of a 
 * fundamental dimension.  A fundamental dimension can have only one fundamental
 * unit, and vice versa.  A {@link FundamentalPair fundamental dimension-unit pair}
 * can be created via {@link Universe#newFundamentalPair}.  The scale factor of
 * a fundamental unit is exactly 1.
 * 
 * <p>A <i>base unit</i> of a dimension is that unit which is composed only of powers of
 * fundamental units with a scale factor of 1.  Every fundamental unit is also a base unit.
 * 
 * <p>The scale of a unit is the number of base units that represent exactly one of this unit.
 * 
 * @author andreww1011
 */
public interface Unit {
    
    /**
     * Returns the name of this unit.  
     */
    String getName();
    
    /**
     * Returns the symbol of this unit.  
     */
    String getSymbol();
    
    /**
     * Returns the dimension of this unit.
     */
    Dimension getDimension();
    
    /**
     * Returns the scale of this unit.
     */
    Scalar getScale();
    
    /**
     * Tests whether the specified object is equal to this unit.  Every 
     * unit is unique even if all defining properties are identical.
     * Thus, this method returns true if and only if the
     * specified object and this unit are the same object.
     * 
     * @param o object to compare to this unit.
     */
    @Override
    boolean equals(Object o);
}
