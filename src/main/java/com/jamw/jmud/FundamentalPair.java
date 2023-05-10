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
 * A fundamental pair is the mapping of a fundamental unit to a fundamental dimension.
 * 
 * @author andreww1011
 */
public interface FundamentalPair {
    
    /**
     * Returns the fundamental dimension mapped to the fundamental unit of this pair.
     *
     * @return a {@link com.jamw.jmud.FundamentalDimension} object
     */
    FundamentalDimension getFundamentalDimension();
    
    /**
     * Returns the fundamental unit mapped to the fundamental dimension of this pair.
     *
     * @return a {@link com.jamw.jmud.FundamentalUnit} object
     */
    FundamentalUnit getFundamentalUnit();
}
