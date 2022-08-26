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

import com.jamw.jmud.Exponent;
import com.jamw.jmud.Exponents;
import static com.jamw.jmud.Exponents.*;
import com.jamw.jmud.Expression;
import static com.jamw.jmud.Expressions.*;
import com.jamw.jmud.Field;
import com.jamw.jmud.Measure;
import com.jamw.jmud.Scalar;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import com.jamw.jmud.fields.DoubleField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * A 3-phase, 50 Hz transmission line 100 km long delivers 20MW at 0.9 p.f.
 * lagging and at 110 kV.  The resistance and reactance of the line per phase per 
 * km are 0.2 Ohm and 0.4 Ohm, respectively, while the capacitance admittance is
 * 2.5 x 10-6 siemen/km/phase.  Using the nominal-T method, calculate: (1) the 
 * current and voltage at the sending end, and (2) the efficiency of transmission.
 * {@see https://www.site.uottawa.ca/~rhabash/ELG4125DGD32007.pdf
 * @author andreww1011
 */
public class ElectricalPowerTransmissionExample {
    
    static final class ComplexDoubleField 
            implements Field<ComplexDoubleField>,
                       Field.Factory<ComplexDoubleField> {

        public static final ComplexDoubleField ZERO = new ComplexDoubleField(0,0);
        public static final ComplexDoubleField ONE = new ComplexDoubleField(1,0);
        public static final ComplexDoubleField ONE_I = new ComplexDoubleField(0,1);
        
        private final double re,im;
        
        private ComplexDoubleField(double real, double imaginary) {
            this.re = real;
            this.im = imaginary;
        }
        
        public static final Factory<ComplexDoubleField> factory() {
            return ZERO;
        }
        
        public double real() {
            return re;
        }
        
        public double imaginary() {
            return im;
        }
        
        public double abs() {
            return Math.hypot(re,im);
        }
        
        public double phase() {
            return Math.atan2(im,re);
        }
        
        public ComplexDoubleField conjugate() {
            return new ComplexDoubleField(re,-im);
        }
        
        @Override
        public ComplexDoubleField of(int value) {
            return new ComplexDoubleField(value,0); //magic number
        }

        private static final String REGEX 
        = "^(?=[iI.\\d+-])(?<real>[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?![iI.\\d]))?((?<imaginary>[+-]?(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)?)[iI])?$";
        private static final Pattern PATTERN = Pattern.compile(REGEX);
        
        @Override
        public ComplexDoubleField of(String value) throws NumberFormatException {
            String v = value.replaceAll(" ","");
            final Matcher matcher = PATTERN.matcher(v);
            if (!matcher.matches())
                throw new NumberFormatException();
            String real = matcher.group("real");
            double r = real == null || real.isEmpty() ? 0. : Double.parseDouble(real);
            String imag = matcher.group("imaginary");
            double i = imag == null || imag.isEmpty() ? 0. : Double.parseDouble(imag);
            return new ComplexDoubleField(r,i);
        }
        
        public static final ComplexDoubleField of(double real,double imaginary) {
            return new ComplexDoubleField(real,imaginary);
        }
        
        public static final ComplexDoubleField of(DoubleField real,DoubleField imaginary) {
            return of(real.value(),imaginary.value());
        }
        
        public static final ComplexDoubleField of(Scalar real,Scalar imaginary) {
            return of(real.using(DoubleField.factory()),imaginary.using(DoubleField.factory()));
        }
        
        @Override
        public Factory<ComplexDoubleField> getFactory() {
            return factory();
        }

        @Override
        public ComplexDoubleField negate() {
            return new ComplexDoubleField(-re,-im);
        }

        @Override
        public ComplexDoubleField reciprocal() throws ArithmeticException {
            double s = re*re + im*im;
            return new ComplexDoubleField(re/s, -im/s);
        }

        @Override
        public ComplexDoubleField add(ComplexDoubleField f) {
            double r = re + f.re;
            double i = im + f.im;
            return new ComplexDoubleField(r,i);
        }

        @Override
        public ComplexDoubleField subtract(ComplexDoubleField f) {
            double r = re - f.re;
            double i = im - f.im;
            return new ComplexDoubleField(r,i);
        }

        @Override
        public ComplexDoubleField multiply(ComplexDoubleField f) {
            double r = re*f.re - im*f.im;
            double i = re*f.im + im*f.re;
            return new ComplexDoubleField(r,i);
        }

        @Override
        public ComplexDoubleField power(ComplexDoubleField exponent) throws ArithmeticException {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ComplexDoubleField power(Exponent exponent) throws ArithmeticException {
            if (im == 0)
                return realPower(this,exponent);
            else
                return complexPower(this,exponent);
        }
        
        private static ComplexDoubleField realPower(ComplexDoubleField f,Exponent e) {
            return new ComplexDoubleField(Math.pow(f.real(),e.numerator()/(double)e.denominator()),0);  //magic number
        }
        
        private static ComplexDoubleField complexPower(ComplexDoubleField f,Exponent e) {
            if (e.isEqualTo(Exponents.ZERO))
                return ONE;
            if (e.isEqualTo(Exponents.ONE))
                return f;
            Exponent e1 = e.isLessThan(Exponents.ZERO) ?
                          Exponents.of(-e.numerator(),e.denominator()) :
                          e;
            double n = e1.numerator()/e1.denominator();
            double r = f.abs();
            double t = f.phase();
            double rn = Math.pow(r,n);
            double re = rn*Math.cos(n*t);
            double im = rn*Math.sin(n*t);
            ComplexDoubleField a = new ComplexDoubleField(re,im);
            if (e.isLessThan(Exponents.ZERO))
                return a.reciprocal();
            else
                return a;
        }

        @Override
        public ComplexDoubleField logarithm(ComplexDoubleField base) throws ArithmeticException {
            throw new UnsupportedOperationException();
        }

        @Override
        public int compareTo(ComplexDoubleField o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) 
                return true;
            if (!(o instanceof ComplexDoubleField))
                return false;
            ComplexDoubleField oc = (ComplexDoubleField)o;
            return re == oc.re && im == oc.im;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 37 * hash + (int)(Double.doubleToLongBits(this.re) ^ (Double.doubleToLongBits(this.re) >>> 32));
            hash = 37 * hash + (int)(Double.doubleToLongBits(this.im) ^ (Double.doubleToLongBits(this.im) >>> 32));
            return hash;
        }
        
        @Override
        public String toString() {
            boolean posi = im >= 0;
            return Double.toString(re) +
                   (posi ? "+" : "-") +
                   Double.toString(Math.abs(im)) +
                   "i";
        }
    }
    
    private static final Unit km = Units.kilo(Units.METER);
    private static final Unit ohm = Units.OHM;
    private static final Unit siemens = Units.SIEMENS;
    private static final Unit kV = Units.kilo(Units.VOLT);
    private static final Unit MW = Units.MEGAWATT;
    private static final Unit amp = Units.AMPERE;
    
    @Test
    public void example() {
        //Givens
        Expression d,R,X,Y,V,P;
        d = take(100,km); //line length
        R = take("0.2",ohm).divide(1,km); //resistance
        X = take("0.4",ohm).divide(1,km); //reactance
        Y = take("2.5e-6",siemens).divide(1,km); //capacitive admittance
        V = take(110,kV); //voltage
        Scalar nPhase = take(3); //number of phases
        P = take(20,MW); //power
        Scalar pf = take("0.9"); //power factor
        
        Expression R_T = R.multiply(d); //total resistance
        Expression X_T = X.multiply(d); //total reactance
        Expression Y_T = Y.multiply(d); //total capacitance admittance
        
        Expression V_R1 = V.divide(nPhase.power(SQUARE_ROOT)); //receiving end voltage
        Expression I_R1 = P.divide(
                nPhase.power(SQUARE_ROOT))
                .divide(V).divide(pf); //receiving end current
        Scalar cosPhi = pf;
        Scalar sinPhi = take(1)
                .subtract(cosPhi.power(SQUARED))
                .power(SQUARE_ROOT);
        
        Measure<ComplexDoubleField> V_R = V_R1.multiply(ComplexDoubleField.ONE); //receiving end voltage
        Measure<ComplexDoubleField> I_R = I_R1.multiply(
                ComplexDoubleField.of(cosPhi,sinPhi.negate())); //receiving end current
        
        Measure<ComplexDoubleField> Z = R_T.multiply(ComplexDoubleField.ONE)
                .add(X_T.multiply(ComplexDoubleField.ONE_I)); //phase impedance
        Measure<ComplexDoubleField> V_1 = V_R.add(I_R.multiply(Z).divide(2)); //shunt voltage
        Measure<ComplexDoubleField> I_C = V_1.multiply(Y_T).multiply(ComplexDoubleField.ONE_I); //shunt current
        Measure<ComplexDoubleField> I_S = I_R.add(I_C); 
        Measure<DoubleField> I_Ss = abs(I_S);//sending end current
        String sendingCurrentInAmps = "110.10";
        double errTol = 1e-3;
        assertTrue(equalsTolerance(Double.valueOf(sendingCurrentInAmps),
                   I_Ss.as(amp).getField().value(),
                   errTol));
        
        Measure<ComplexDoubleField> V_S = V_1.add(I_S.multiply(Z).divide(2)); 
        Measure<DoubleField> V_Ss = abs(V_S).multiply(nPhase.power(SQUARE_ROOT)); //sending end voltage
        String sendingVoltageInkV = "116.72";
        assertTrue(equalsTolerance(Double.valueOf(sendingVoltageInkV),
                   V_Ss.as(kV).getField().value(),
                   errTol));
        
        Measure<DoubleField> loss = nPhase.multiply(R_T).divide(2).multiply(
                I_Ss.power(SQUARED).add(I_R1.power(SQUARED)));
        Measure<DoubleField> efficiency = P.divide(P.add(loss));
        String percentEfficiency = "96.29";
        assertTrue(equalsTolerance(Double.valueOf(percentEfficiency),
                   efficiency.as(Units.PERCENT).getField().value(),
                   errTol));
    }
    
    private static Measure<DoubleField> abs(Measure<ComplexDoubleField> measure) {
        return take(DoubleField.of(measure.getField().abs()),measure.getUnit());
    }
    
    private static boolean equalsTolerance(double a, double b, double tolerance) {
        double diff = Math.abs(a-b);
        double min = Math.min(Math.abs(a),Math.abs(b));
        return diff/min <= tolerance;
    }
}
