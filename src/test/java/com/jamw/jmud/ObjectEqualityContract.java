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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andreww1011
 */
public interface ObjectEqualityContract<T> {
    
    T createObject();
    
    T createDifferentObject();
    
    @Test
    default void objectEqualsItself() {
        T value = createObject();
        assertEquals(value,value);
    }
    
    @Test
    default void objectDoesNotEqualNull() {
        T value = createObject();
        assertFalse(value.equals(null));
    }
    
    @Test
    default void objectDoesNotEqualADifferentObject() {
        T value = createObject();
        T differentValue = createDifferentObject();
        assertNotEquals(value,differentValue);
        assertNotEquals(differentValue,value);
    }    
}
