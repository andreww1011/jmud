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
package com.jamw.jmud.examples;

import com.jamw.jmud.Constants;
import com.jamw.jmud.Exponents;
import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import com.jamw.jmud.Measure;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import static com.jamw.jmud.examples.Utils.equalsWithinUlp;
import com.jamw.jmud.fields.BigDecimal128Field;
import com.jamw.jmud.fields.DoubleField;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * The force between two identical charges separated by 1 cm is equal to 90 N.  
 * What is the magnitude of the two charges?
 * @see https://sciencenotes.org/coulombs-law-example-problem/
 * @author andreww1011
 */
public class CoulombsLawExample {
    
    private static final Expression ONE_OVER_k_e = 
            Expressions.take(4).multiply(Constants.pi).multiply(Constants.eps_0);
    
    private static Expression solveForCharge(Expression force, Expression distance) {
        return  force
                .multiply(distance).multiply(distance)
                .multiply(ONE_OVER_k_e)
                .power(Exponents.SQUARE_ROOT);
    } 
    
    @Test
    public void example() {
        Expression force = Expressions.take(90,Units.NEWTON);
        Expression distance = Expressions.take(1,Units.CENTIMETER);
        Expression charge = solveForCharge(force,distance);
        Unit microCoulomb = Units.micro((Units.COULOMB));
        Measure<DoubleField> chargeDouble = charge.using(DoubleField.factory()).as(microCoulomb);
        Measure<BigDecimal128Field> chargeBigDecimal = charge.using(BigDecimal128Field.factory()).as(microCoulomb);
        String answerInMicroCoulomb = "1.0006922853220581";
        assertTrue(equalsWithinUlp(chargeDouble.getField().value(),chargeBigDecimal.getField().value().doubleValue()));
        assertTrue(Double.toString(chargeDouble.getField().value()).equals(answerInMicroCoulomb));
    }
}
