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
 * A dimension represents a property of a phenomenon, body, or substance that can 
 * be quantified by measurement.  
 * 
 * <p>The {@link Composition composition} of a dimension can be represented as a
 * set of pairs of {@link FundamentalDimension fundamental dimensions} and 
 * rational number {@link Exponent exponents} describing the degree by which
 * each fundamental dimension is spanned.  In mathematical terms, a dimension
 * forms an abelian group by way of its composition of a vector space over the 
 * rational numbers, where the set of fundamental dimensions form the basis of the 
 * vector space, and the corresponding exponents are the values.  The origin of the vector 
 * space (the group identity 1) corresponds to the null vector (that is, the
 * value of the exponent in every fundamental dimension-exponent pair comprising 
 * the dimension's composition, is zero), and is interpreted as the special dimension
 * of dimensionless measurements represented by the object 
 * {@link com.jamw.jmud.Universe#getDimensionless()}.
 * 
 * <p>When physical quantities,
 * (with equal or non-equal composition) are multiplied or divided by one
 * another, their fundamental dimensions are likewise multiplied or divided;  
 * this corresponds to addition and subtraction, respectively, in the vector
 * space.  When physical quantities are raised to a rational power, their 
 * fundamental dimensions are likewise raised to that power;  this corresponds
 * to scalar multiplication in the vector space.  Dimensions that have the 
 * same composition are said to be commensurable and likewise
 * describe a property of the same or related phenomenon
 * in such a way that the measurements may be linearly combined in
 * a meaningful way.
 * 
 * <p>For example, given the set of fundamental dimensions <i>length</i>, 
 * denoted <i>[L]</i>, <i>mass [M]</i>, and <i>time [T]</i> to form a basis,
 * the dimension of the physical quantity <i>acceleration</i>
 * is defined as <i>length/time<sup>2</sup></i>, where the fundamental
 * dimensions are combined to form the composition <i>Q = [LT<sup>-2</sup>]</i>.
 * The dimension of the physical quantity <i>force</i> is defined as 
 * <i>mass * acceleration</i> and the composition is 
 * <i>Q = [MLT<sup>-2</sup>]</i>.  Acceleration and force are not commensurable,
 * as clearly seen by their compositions.  Indeed, it is nonsensical to take
 * a measurement of acceleration and add or subtract it to a measurement of force.  However,
 * multiplying or dividing together measurements of acceleration and force
 * does make sense.  It is, in fact, through this mechanism that the
 * dimension of force was defined from the multiplication of the dimensions of mass and acceleration.
 * 
 * @author andreww1011
 */
public interface Dimension {
    
    /**
     * Asserts that the specified dimensions are commensurable.
     *
     * @param d1 dimension to compare 
     * @param d2 dimension to compare
     * @throws com.jamw.jmud.IncommensurableDimensionException if the dimensions are not commensurable.
     */
    static void assertCommensurable(Dimension d1, Dimension d2) throws IncommensurableDimensionException {
        if (!d1.isCommensurable(d2))
            throw new IncommensurableDimensionException(d1.getName() + " and " + d2.getName() + " are not commensurable.");
    } 
    
    /**
     * Returns the name of this dimension.  
     *
     * @return a {@link java.lang.String} object
     */
    String getName();
    
    /**
     * Returns the symbol of this dimension.
     *
     * @return a {@link java.lang.String} object
     */
    String getSymbol();
    
    /**
     * Returns the composition of this dimension.
     *
     * @return a {@link com.jamw.jmud.Composition} object
     */
    Composition getComposition();
    
    /**
     * Returns true if the specified dimension is commensurable with this
     * dimension.  
     * 
     * @param d dimension to be compared to this dimension for commensurability .
     * @return a boolean
     */
    default boolean isCommensurable(Dimension d) {
        if (this.equals(d))
            return true;
        else
            return getComposition().equals(d.getComposition());
    }
    
    /**
     * {@inheritDoc}
     *
     * Tests whether the specified object is equal to this dimension.  Every 
     * dimension is unique even if all defining properties are identical.
     * Thus, this method returns true if and only if the
     * specified object and this dimension are the same object.
     * 
     * @param o object to compare to this dimension.
     */
    @Override
    boolean equals(Object o);
    
}
