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
 * One {@link FundamentalDimension fundamental dimension}-{@link Exponent exponent}
 * pair contained in the set of pairs comprising a dimension's composition.
 *
 * @author andreww1011
 */
public interface CompositionComponent {
    
    /**
     * The fundamental dimension of this composition component.
     *
     * @return a fundamental dimension.
     */
    FundamentalDimension fundamentalDimension();
    
    /**
     * The exponent associated with the fundamental dimension
     * of this composition component.
     *
     * @return an exponent.
     */
    Exponent exponent();
    
    /**
     * {@inheritDoc}
     *
     * Compares the specified object with this composition component for equality.
     * Returns true if the specified object is also a composition component and the two
     * composition components contain the same fundamental dimension
     * and exponent.
     * 
     * @param o object to be compared for equality to this composition component.
     * 
     * @return true if the specified object is equal to this composition component.
     */
    @Override
    public boolean equals(Object o);
}
