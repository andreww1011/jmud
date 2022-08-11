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
 *
 * @author andreww1011
 */
public interface Magnitude<F extends Field<F>> extends Comparable<Magnitude<F>>{
    
    F getValue();
    
    Scale getScale();
    
    Measure<F> getMeasure();
        
    @Override
    boolean equals(Object o);
    
    @Override
    String toString();
    
    @Override
    public int compareTo(Magnitude<F> o) throws IncommensurableDimensionException;
    
    @Override
    default boolean isEqualTo(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) == 0;
    }
    
    @Override
    default boolean isLessThan(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) < 0;
    }
    
    @Override
    default boolean isGreaterThan(Magnitude<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) > 0;
    }
}
