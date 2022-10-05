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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author andreww1011
 */
public interface FundamentalDimensionContract extends ObjectEqualityContract<FundamentalDimension> {
    
    FundamentalDimension getFundamentalDimension();
    
    @Override
    default FundamentalDimension createObject() {
        return getFundamentalDimension();
    }
    
    String expectedName();
    
    String expectedSymbol();
    
    @Test
    default void testFundamentalDimensionName() {
        assertEquals(getFundamentalDimension().getName(),expectedName());
    }
    
    @Test
    default void testFundamentalDimensionSymbol() {
        assertEquals(getFundamentalDimension().getSymbol(),expectedSymbol());
    }
    
    @Test
    default void testFundamentalDimensionCompositionContainsOnlyItselfToPowerOne() {
        Iterator<CompositionComponent> iterator = getFundamentalDimension().getComposition().iterator();
        List<CompositionComponent> list = Stream.generate(() -> null)
                .takeWhile(x -> iterator.hasNext())
                .map(n -> iterator.next())
                .collect(Collectors.toUnmodifiableList());
        int listSize = list.size();
        assertTrue(listSize == 1); //magic number
        CompositionComponent cc = list.get(0); //magic number
        assertEquals(cc.fundamentalDimension(),getFundamentalDimension());
        assertTrue(cc.exponent().isEqualTo(Exponents.ONE));
    }
}
