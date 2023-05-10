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
 * A level is the value of a measurement in a particular {@link Scale scale}.
 * This class has a natural ordering that is inconsistent with equals.
 *
 * @author andreww1011
 */
public interface Level<F extends Field<F>> extends Comparable<Level<F>>{
    
    /**
     * Returns the field of this level.
     *
     * @return a F object
     */
    F getField();
    
    /**
     * Returns the scale of this level.
     *
     * @return a {@link com.jamw.jmud.Scale} object
     */
    Scale getScale();
    
    /**
     * Returns the measure associated with the value of this level.
     *
     * @return a {@link com.jamw.jmud.Measure} object
     */
    Measure<F> getMeasure();
        
    /**
     * {@inheritDoc}
     *
     * Compares the specified object with this level for equality. 
     * Returns true if the specified object is a level and the two levels
     * have the same scale and the fields represent the same value.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @param o object to be compared for equality to this field.
     */
    @Override
    boolean equals(Object o);
    
    /**
     * {@inheritDoc}
     *
     * Compares this level with the specified level for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified level.
     * 
     * <p>If this and the specified level have the same scale, this method
     * provides the same behavior as {@link Field#compareTo}.  If the two
     * levels have different scales, but the dimensions of their underlying units 
     * are commensurable, this method provides the same behavior as 
     * {@link Measure#compareTo}.  Otherwise, an exception is thrown.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @param   o the level to be compared.
     * @return  a negative integer, zero, or a positive integer as this level
     *          is less than, equal to, or greater than the specified level.
     * @throws IncommensurableDimensionException if the dimensions of the 
     * underlying units of this and the specified level are incommensurable.
     */
    @Override
    public int compareTo(Level<F> o) throws IncommensurableDimensionException;
    
    /**
     * {@inheritDoc}
     *
     * Returns true if this level and the specified level are equal in value.  
     * Two levels are considered equal if the {@link equals} method returns true, or 
     * in the case that the scales of the levels are different, but the
     * dimension of their underlying units are commensurable, if their measures
     * are equivalent.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @param   o the level to be compared.
     * @throws IncommensurableDimensionException if the dimensions of the 
     * underlying units of this and the specified level are incommensurable.
     */
    @Override
    default boolean isEqualTo(Level<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) == 0;
    }
    
    /**
     * {@inheritDoc}
     *
     * Returns true if the value of this level is less than the specified level. 
     * 
     * <p>If this and the specified level have the same scale, this method
     * provides the same behavior as {@link Field#isLessThan}.  If the two
     * levels have different scales, but the dimensions of their underlying units 
     * are commensurable, this method provides the same behavior as 
     * {@link Measure#isLessThan}.  Otherwise, an exception is thrown.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @param   o the level to be compared.
     * @throws IncommensurableDimensionException if the dimensions of the 
     * underlying units of this and the specified level are incommensurable.
     */
    @Override
    default boolean isLessThan(Level<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) < 0;
    }
    
    /**
     * {@inheritDoc}
     *
     * Returns true if the value of this level is greater than the specified level. 
     * 
     * <p>If this and the specified level have the same scale, this method
     * provides the same behavior as {@link Field#isGreaterThan}.  If the two
     * levels have different scales, but the dimensions of their underlying units 
     * are commensurable, this method provides the same behavior as 
     * {@link Measure#isGreaterThan}.  Otherwise, an exception is thrown.
     * 
     * <p>Note: this class has a natural ordering that is inconsistent with equals.
     * 
     * @param   o the level to be compared.
     * @throws IncommensurableDimensionException if the dimensions of the 
     * underlying units of this and the specified level are incommensurable.
     */
    @Override
    default boolean isGreaterThan(Level<F> o) throws IncommensurableDimensionException {
        return this.compareTo(o) > 0;
    }
}
