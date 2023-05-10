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

/**
 * The set of pairs of {@link FundamentalDimension fundamental dimensions} and
 * rational number {@link Exponent exponents} comprising a dimension's
 * composition.
 * 
 * <p>Two dimensions are commensurable if the sets of fundamental dimension - 
 * non-zero exponent pairs comprising the composition of each dimension are the
 * same, exclusive of the pair containing the special  
 * {@link com.jamw.jmud.Universe#getDimensionless dimensionless} fundamental dimension.
 * 
 * <p>A dimension whose composition is the null set, or whose composition comprises
 * only of exponents with a value of zero, is equivalent to the dimensionless
 * fundamental dimension.  The composition of the dimensionless fundamental 
 * dimension is itself with an exponent value of one.
 * 
 * @author andreww1011
 */
public interface Composition extends Iterable<CompositionComponent> {
    
    /**
     * Returns the exponent of the specified fundamental dimension of this
     * composition.
     *
     * @param fd fundamental dimension in composition.
     * @return exponent corresponding to the specified fundamental dimension.
     */
    Exponent getExponent(FundamentalDimension fd);
    
    /**
     * {@inheritDoc}
     *
     * Returns an iterator of composition components comprising this composition.
     *  
     * Fundamental dimensions with an exponent value of zero may or may not be
     * traversed by this iterator.  Implementations should not rely on this method
     * to return all possible fundamental dimensions.  Modifying this composition 
     * via the iterator is not permitted.
     * 
     * @return an iterator of composition components of this composition.
     */
    @Override
    Iterator<CompositionComponent> iterator();
    
    /**
     * Compares the specified object with this composition for equality. 
     * Returns true if the given object is also a composition and the two 
     * compositions have the same set of mapped fundamental dimension-to-exponent
     * pairs for all non-zero valued exponents.
     * Fundamental dimensions with an exponent of zero are explicitly 
     * not considered in the equality test.  For 
     * instance, the compositions <i>Q1 = [L<sup>2</sup>]</i> and 
     * <i>Q2 = [L<sup>2</sup>T<sup>0</sup>]</i> are, in fact, equal.
     * 
     * @param o object to be compared for equality to this composition.
     * @return true if the specified object is equal to this composition.
     */
    boolean equals(Object o);
}
