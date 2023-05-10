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
 * A fundamental dimension is a special 
 * {@link Dimension Dimension} that together with other fundamental dimensions forms a basis 
 * for describing the composition of all other dimensions.  One may choose any set
 * of fundamental dimensions to form the basis of a {@link com.jamw.jmud.Universe}.
 * <p>
 * A fundamental dimension itself has a composition of itself raised to the 
 * power one;  all other powers are zero.  
 * <p>
 * A fundamental dimension must be described by a non-empty {@link String String} 
 * <i>name</i> and <i>symbol</i>.  
 * <p>
 * It is not permissible to have separate instances represent the same
 * fundamental dimension, say <i>length</i>, even though they both might have 
 * the name <i>"LENGTH"</i> and symbol <i>"L"</i>.  The equality
 * of two fundamental dimensions is always implemented as object equality.
 * Therefore, all instances of this interface are unique.  
 * 
 * @author andreww1011
 */
public interface FundamentalDimension extends Dimension {
    
    /**
     * Returns the fundamental unit paired to this fundamental dimension.
     *
     * @return a {@link com.jamw.jmud.FundamentalUnit} object
     */
    FundamentalUnit getFundamentalUnit();
}
