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
 * Represents a rational number to be used as an exponent
 * in a power operation.  A rational number is any number where its value can be 
 * expressed as the quotient of two numbers, <i>p/q</i>, where <i>p</i> is 
 * an integer numerator and <i>q</i> is a positive integer denominator. 
 * 
 * <p>The value of this exponent is equal to <i>p/q</i> exactly, 
 * but the actual fraction is always reduced to its simplest form and the
 * denominator is always positive.  The value of zero is represented as "0/1".
 * 
 * <p>Two exponents are considered equal if and only if their numerators are
 * equal and their denominators are equal.
 * 
 * @author andreww1011
 */
public interface Exponent extends Comparable<Exponent>{
    
    /**
     * Returns the numerator of this rational number exponent.
     */
    int numerator();
    
    /**
     * Returns the denominator of the rational number exponent.  The denominator
     * must be a positive integer.
     */
    int denominator();
    
    /**
     * Compares the specified object with this exponent for equality. 
     * Returns true if the specified object is an exponent and the two 
     * exponents represent the same value.
     * @param o object to be compared for equality to this exponent.
     * @return 
     */
    @Override
    boolean equals(Object o);
}
