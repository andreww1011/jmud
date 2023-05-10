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
 * Extension of {@link java.lang.Comparable} declaring human-readable
 * comparison methods.
 *
 * @param <T> the type of objects that this object may be compared to
 * @author andreww1011
 */
public interface Comparable<T> extends java.lang.Comparable<T> {
    
    /**
     * Tests if the natural order of this object and the specified object
     * are identical.
     *
     * @param t object to compare to this object
     * @throws java.lang.NullPointerException if the specified object is null.
     * @throws java.lang.ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     * @return true if the natural order of this object is equal to the 
     *         specified object, otherwise false.
     */
    default public boolean isEqualTo(T t) {
        return this.compareTo(t) == 0;
    }
    
    /**
     * Tests if the natural order of this object occurs before the specified
     * object.
     *
     * @param t object to compare to this object
     * @throws java.lang.NullPointerException if the specified object is null.
     * @throws java.lang.ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     * @return true if the natural order of this object is before the 
     *         specified object, otherwise false.
     */
    default public boolean isLessThan(T t) {
        return this.compareTo(t) < 0;
    }
    
    /**
     * Tests if the natural order of this object occurs after the specified
     * object.
     *
     * @param t object to compare to this object
     * @throws java.lang.NullPointerException if the specified object is null.
     * @throws java.lang.ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     * @return true if the natural order of this object is after the 
     *         specified object, otherwise false.
     */
    default public boolean isGreaterThan(T t) {
        return this.compareTo(t) > 0;
    }
}
