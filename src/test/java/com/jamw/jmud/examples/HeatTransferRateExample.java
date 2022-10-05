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

import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import com.jamw.jmud.Measure;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import com.jamw.jmud.fields.DoubleField;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * A window pane 1.2 m wide and 1.8 m high has a thickness of 6.2 mm.  The thermal
 * conductivity of glass is 0.27 W/m/°C.  What is the rate of heat transfer 
 * through the window if the temperature inside the home is 21°C and -4°C outside?
 * @see https://www.physicsclassroom.com/class/thermalP/Lesson-1/Rates-of-Heat-Transfer
 * @author andreww1011
 */
public class HeatTransferRateExample {
    
    private static Expression rateOfHeatTransfer(Expression area, 
                                  Expression thermalConductivity,
                                  Expression deltaT, 
                                  Expression materialThickness) {
        return thermalConductivity
                .multiply(area)
                .multiply(deltaT)
                .divide(materialThickness);
    }
    
    @Test
    public void example() {
        Unit degreeCelsius = Units.newUnit()
                .asExactly(1).ofA(Units.KELVIN)
                .withSymbol("°C")
                .create();
        Unit wattPerMeterDegreeCelsius = Units.newUnit()
                .as(Units.WATT)
                .divide(Units.METER)
                .divide(degreeCelsius)
                .create();
        
        Expression width = Expressions.take("1.2",Units.METER);
        Expression height = Expressions.take("1.8",Units.METER);
        Expression thickness = Expressions.take("6.2",Units.MILLIMETER);
        Expression area = width.multiply(height);
        Expression thermalConductivity 
                = Expressions.take("0.27",wattPerMeterDegreeCelsius);
        Expression insideTemp = Expressions.take(21,degreeCelsius);
        Expression outsideTemp = Expressions.take(-4,degreeCelsius);
        Expression deltaT = insideTemp.subtract(outsideTemp);
        Expression rateOfHeatTransfer 
                = rateOfHeatTransfer(area,
                                     thermalConductivity,
                                     deltaT,
                                     thickness);
        Measure<DoubleField> rateDouble = rateOfHeatTransfer
                .using(DoubleField.factory())
                .as(Units.KILOWATT);
        String answerInKilowatt = "2.351612903225807";
        
        assertTrue(Double.toString(rateDouble.getField().value()).equals(answerInKilowatt));
    }
    
}
