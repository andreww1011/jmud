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

import com.jamw.jmud.Dimension;
import com.jamw.jmud.Exponents;
import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import static com.jamw.jmud.Expressions.*;
import com.jamw.jmud.Field;
import com.jamw.jmud.Measure;
import com.jamw.jmud.Scalar;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import com.jamw.jmud.fields.DoubleField;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Determine the minimum required number of #8 longitudinal bars and maximum 
 * spacing of #4 stirrups for a simply supported reinforced concrete beam with the
 * following properties:
 * <ul>
 * <li>Dead load: 1500 lb/ft</li>
 * <li>Live load: 800 lb/ft</li>
 * <li>Beam length: 20 ft</li>
 * <li>Beam width: 16 in</li>
 * <li>Beam depth: 24 in</li>
 * <li>Cover: 1.5 in</li>
 * <li>Concrete compressive strength: 4000 psi</li>
 * <li>Steel yield strength: 60000 psi ft</li>
 * <li>Support column size: 12" x 12"</li>
 * </ul>
 * {@see https://civilengineeringbible.com/subtopics.php?i=32}
 * @author andreww1011
 */
public class ConcreteBeamDesignExample {
    
    private static Expression b1_max =take("0.85",Units.UNITLESS);
    private static Expression b1_min =take("0.65",Units.UNITLESS);
    
    private static Unit in = Units.newUnit().as(Units.INCH).withSymbol("in").create();
    private static Unit ft = Units.newUnit().as(Units.FOOT).withSymbol("ft").create();
    private static Unit sqin = Units.newUnit().as(in,2).withSymbol("in^2").create();
    private static Unit psi = Units.newUnit().as(Units.POUND_PER_SQUARE_INCH).withSymbol("psi").create();
    private static Unit plf = Units.newUnit().as(Units.POUND_PER_FOOT).withSymbol("plf").create();
    private static Unit pcf = Units.newUnit().as(Units.POUND_PER_CUBIC_FOOT).withSymbol("pcf").create();
    private static Unit root_psi = Units.newUnit().as(psi,1,2).withSymbol("\u221Apsi").create();
    
    private static Expression b1(Expression fc) {
        Expression b1 = b1_max.subtract(
                take("0.05").multiply(
                        fc.subtract(4000,psi))
                .divide(1000,psi));        
        return max(min(b1_max,b1),b1_min);
    }
    
    public static final Expression min(Expression... expressions) {
        if (checkMinMaxArgument(expressions)) 
            return expressions[0]; //magic number
        Dimension d = expressions[0].getDimension(); //magic number
        checkMinMaxDimension(d,expressions);
        Function<Field.Factory,Measure> g = (factory) ->
            Arrays.asList(expressions).stream()
                    .map((e) -> e.using(factory))
                    .min((m1,m2) -> m1.compareTo(m2))
                    .get();
        return Expressions.take(g,d);
    }
    
    @SafeVarargs
    private static boolean checkMinMaxArgument(Expression... expressions) {
        Objects.requireNonNull(expressions);
        if (expressions.length == 0) //magic number
            throw new IllegalArgumentException();
        return expressions.length == 1;
    }
    
    private static void checkMinMaxDimension(Dimension d, Expression... expressions) {
        Arrays.asList(expressions).stream()
                .forEach((e) -> Dimension.assertCommensurable(d,e.getDimension()));
    }
    
    public static final Expression max(Expression... expressions) {
        if (checkMinMaxArgument(expressions)) 
            return expressions[0]; //magic number
        Dimension d = expressions[0].getDimension(); //magic number
        checkMinMaxDimension(d,expressions);
        Function<Field.Factory,Measure> g = (factory) ->
            Arrays.asList(expressions).stream()
                    .map((e) -> e.using(factory))
                    .max((m1,m2) -> m1.compareTo(m2))
                    .get();
        return Expressions.take(g,d);
    }
   
    @Test
    public void example() {
        Field.Factory<DoubleField> factory = DoubleField.factory();
        
        //Properties
        Expression DL,LL,L,b,d,cc,dl,ds,fc,fy,w_col;
        DL = take(1500,plf); //dead load
        LL = take(800,plf); //live load
        L = take(20,ft); //beam length
        b = take(16,in); //width
        d = take(24,in); //depth
        cc = take("1.5",in); //clear cover
        dl = take(1,in); //longitudinal bar diameter
        ds = take("0.5",in); //stirrup diameter
        fc = take(4000,psi); //concrete compressive strength
        fy = take(60000,psi); //steel yeild strength
        w_col = take(12,in); //support column width
        
        //Design for flexure
        Expression A = b.multiply(d); //cross-sectional area
        Expression rho_c = take(150,pcf); //concrete density
        Expression SW = A.multiply(rho_c); //beam self weight
        Expression w_u = take("1.4").multiply(DL.add(SW))
                .add(take("1.7").multiply(LL)); //factored design load
        Expression M_u = w_u.multiply(L.power(Exponents.SQUARED)).divide(8); // factored moment
        Expression de = d.subtract(cc).subtract(ds)
                .subtract(dl.divide(2)); //longitudinal bar effective depth
        Scalar phi_m = take("0.9"); //resistance factor
        Expression R_n = M_u.divide(phi_m.multiply(b).multiply(de.power(Exponents.SQUARED))); //nominal stress
        Expression m = fy.divide(take("0.85").multiply(fc)); //strength ratio
        Expression r = 
                take(1).subtract(
                        take(1).subtract(
                                take(2).multiply(m).multiply(R_n).divide(fy)
                        ).power(Exponents.SQUARE_ROOT)
                ).divide(m); //required reinforcement ratio
        Expression r_min = take(200,psi).divide(fy); //minimum reinforcement ratio
        r = max(r,r_min);
        Expression r_max = take("0.75").divide(m).multiply(b1(fc))
                .multiply(87000,psi).divide(
                        take(87000,psi).add(fy)); //maximum reinforcement ratio
        //Check reinforcement ratio is less than max
        assertTrue(r.using(factory).isLessThan(r_max.using(factory)));
        
        Expression As_req = r.multiply(b).multiply(de); //area of longitudinal steel required
        Unit bar8 = Units.newUnit().asExactly("0.79").ofA(sqin).withSymbol("#8 bar").create();
        Measure<DoubleField> num8Bars = DoubleField.Measure.ceil(As_req.using(factory).as(bar8));
        
        //Check answer
        Measure<DoubleField> answer = take(3,bar8).using(factory);
        assertTrue(num8Bars.isEqualTo(answer));
        
        //Design for shear
        Expression Ln = L.subtract(take(2).multiply(w_col).divide(2)); //clear span between supports
        Expression V_u = w_u.multiply(Ln).divide(2); //factored shear
        Expression V_c = take(2,root_psi)
                .multiply(fc.power(Exponents.SQUARE_ROOT))
                .multiply(b.multiply(de)); //concrete shear strength
        Scalar phi_v = take("0.85");
        Expression V_s_req = V_u.divide(phi_v).subtract(V_c); //required steel strength
        
        Expression s_max = de.divide(2); //maximum spacing
        Unit bar4 = Units.newUnit().asExactly("0.2").ofA(sqin).withSymbol("#4 bar").create();
        Expression V_s_prov = take(1,bar4).multiply(fy).multiply(de).divide(s_max); //provided steel strength
        assertTrue(V_s_prov.using(factory).isGreaterThan(V_s_req.using(factory)));
    }
}
