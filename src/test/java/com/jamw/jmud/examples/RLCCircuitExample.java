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

import com.jamw.jmud.Exponents;
import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import com.jamw.jmud.Measure;
import com.jamw.jmud.Units;
import com.jamw.jmud.fields.DoubleField;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * An RLC circuit has an inductive reactance, capacitive reactance, and resistance
 * of 184 \u03A9, 144 \u03A9, and 30 \u03A9, respectively.  Find the impedance
 * and the phase angle between voltage and current.
 * @see https://www.brainkart.com/article/Solved-Example-Problems-on-Alternating-Current-(AC)-and-Circuit_38526/
 * @author andreww1011
 */
public class RLCCircuitExample {
    
    private static Expression impedance(Expression L, Expression C, Expression R) {
        return R.power(Exponents.SQUARED)
                .add(L.subtract(C).power(Exponents.SQUARED))
                .power(Exponents.SQUARE_ROOT);
    }
    
    private static Measure<DoubleField> atan(Expression e) {
        double r = e.using(DoubleField.factory()).as(Units.UNITLESS).getField().value();
        double rad = Math.atan(r);
        return Expressions.take(DoubleField.of(rad),Units.RADIAN);
    }
    
    @Test
    public void example() {
        Expression X_L = Expressions.take(184,Units.OHM);
        Expression X_C = Expressions.take(144,Units.OHM);
        Expression R   = Expressions.take(30,Units.OHM);
        Expression Z   = impedance(X_L,X_C,R);
        Measure<DoubleField> Zm = Z.using(DoubleField.factory()).as(Units.OHM);
        String answerOhm = "50.0";
        assertTrue(Double.toString(Zm.getField().value()).equals(answerOhm));
        
        Expression r = X_L.subtract(X_C).divide(R);
        Measure<DoubleField> phaseAngle = atan(r).as(Units.DEGREE);
        String answerPhaseAngleInDegree = "53.1";
        assertTrue(String.format("%.1f",phaseAngle.getField().value()).equals(answerPhaseAngleInDegree));
    }
}
