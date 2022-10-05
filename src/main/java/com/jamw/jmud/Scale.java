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
 * A scale is an alternative representation of a measurement relative to a 
 * reference unit.  A {@link Level level} is the value of a measurement in
 * a particular scale.  
 * 
 * <p>A scale must define the relationship between a measure and its corresponding
 * level, and vice versa.  
 *
 * @author andreww1011
 */
public interface Scale {
    
    /**
     * Returns the name of this scale.  
     */
    String getName();
    
    /**
     * Returns the symbol of this scale.  
     */
    String getSymbol();
    
    /**
     * Returns the reference unit of this scale.
     */
    Unit getReferenceUnit();
    
    /**
     * Creates a level of the specified field value.
     * @param <T> the type of field in which to represent values.
     * @param value the value of the level.
     */
    <T extends Field<T>> Level<T> of(T value);
        
    /**
     * Returns a level representing the value of the specified measure in this scale.
     * @param <T> the type of field in which to represent values.
     * @param measure a measure from which to obtain a level.
     */
    <T extends Field<T>> Level<T> level(Measure<T> measure);
}
