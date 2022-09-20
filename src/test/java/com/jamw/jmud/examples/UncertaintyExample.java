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
import com.jamw.jmud.Exponent;
import com.jamw.jmud.Exponents;
import com.jamw.jmud.Expression;
import com.jamw.jmud.Expressions;
import com.jamw.jmud.Field;
import com.jamw.jmud.IncommensurableDimensionException;
import com.jamw.jmud.Scalar;
import com.jamw.jmud.Unit;
import com.jamw.jmud.Units;
import com.jamw.jmud.fields.DoubleField;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static com.jamw.jmud.examples.UncertaintyExample.*;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Example H.2 of JCGM 100:2008 (GUM 1995 with minor corrections).
 * <p>Simultaneous resistance and reactance measurement.
 * {@see https://www.bipm.org/documents/20126/2071204/JCGM_100_2008_E.pdf/}
 * @author andreww1011
 */
public class UncertaintyExample {
    
    static final class UncertainDoubleField 
            implements Field<UncertainDoubleField>, 
                       Field.Factory<UncertainDoubleField> {

        private static final String REGEX 
        ="^(?<value>[+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?![iI.\\d]))(?:(?:(?:\\+/-|,|\\+-|±|∓)(?<uncertainty1>(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)))|\\((?<uncertainty2>(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?))\\))?$";
        private static final Pattern PATTERN = Pattern.compile(REGEX);
        private static final String VALUE_GROUP_LABEL = "value";
        private static final String UNCERTAINTY_GROUP_LABEL_1 = "uncertainty1";
        private static final String UNCERTAINTY_GROUP_LABEL_2 = "uncertainty2";

        public static final UncertainDoubleField ZERO = new UncertainDoubleField(0.0,0.0);
        public static final UncertainDoubleField ONE = new UncertainDoubleField(1.0,0.0);

        private static final UncertainDoubleField NEGATIVE_ONE = new UncertainDoubleField(-1.0,0);
        private static final UncertainDoubleField E = Constants.euler.using(factory());

        public static final Field.Factory<UncertainDoubleField> factory() {
            return ZERO;
        }

        public static final UncertainDoubleField of(int value, int uncertainty) {
            return of((double)value,uncertainty);
        }

        public static final UncertainDoubleField of(String value, String uncertainty) {
            return of(Double.parseDouble(value),Double.parseDouble(uncertainty));
        }

        public static final UncertainDoubleField of(DoubleField value, DoubleField uncertainty) {
            return of(value.value(),uncertainty.value());
        }

        public static final UncertainDoubleField of(double value, double uncertainty) {
            return new UncertainDoubleField(value,Math.abs(uncertainty));
        }

        private final double value,uncertainty;

        private UncertainDoubleField(double value, double uncertainty) {
            this.value = value;
            this.uncertainty = uncertainty;
        }

        public double value() {
            return value;
        }

        public double uncertainty() {
            return uncertainty;
        }
        
        @Override
        public UncertainDoubleField zero() {
            return ZERO;
        }
        
        @Override
        public UncertainDoubleField one() {
            return ONE;
        }

        @Override
        public UncertainDoubleField of(int value) {
            return of(value,0); //magic number
        }

        @Override
        public UncertainDoubleField of(String value) throws NumberFormatException {
            String v = value.replaceAll(" ","");
            final Matcher matcher = PATTERN.matcher(v);
            if (!matcher.matches())
                throw new NumberFormatException();
            String meas = matcher.group(VALUE_GROUP_LABEL);
            double m = Double.parseDouble(meas);
            String unc = matcher.group(UNCERTAINTY_GROUP_LABEL_1);
            unc = unc == null ? matcher.group(UNCERTAINTY_GROUP_LABEL_2) : unc;
            double e = unc == null || unc.isEmpty() ? 0 : Double.parseDouble(unc);
            return of(m,e);
        }

        @Override
        public Field.Factory<UncertainDoubleField> getFactory() {
            return ZERO;
        }

        @Override
        public UncertainDoubleField negate() {
            return new UncertainDoubleField(-value,uncertainty);
        }

        @Override
        public UncertainDoubleField reciprocal() throws ArithmeticException {
            double v = 1/value;
            double A = value*value;
            double u = uncertainty/A;
            return new UncertainDoubleField(v,u);
        }

        @Override
        public UncertainDoubleField add(UncertainDoubleField f) {
            return add(f,0); //magic number
        }

        public UncertainDoubleField add(UncertainDoubleField f, double correlation) {
            return addOrSubtract(value,uncertainty,f.value(),f.uncertainty(),true,correlation); //magic number
        }

        private static UncertainDoubleField addOrSubtract(double value_a,
                                                          double sigma_a, 
                                                          double value_b,
                                                          double sigma_b, 
                                                          boolean addition, 
                                                          double correlation) {
            double v = value_a + (addition ? value_b : -value_b);
            double u = Math.sqrt(sigma_a*sigma_a + sigma_b*sigma_b 
                    + (addition ? 2 : -2)*correlation*sigma_a*sigma_b); //magic number
            return new UncertainDoubleField(v,u);
        }

        @Override
        public UncertainDoubleField subtract(UncertainDoubleField f) {
            return subtract(f,0); //magic number
        }

        public UncertainDoubleField subtract(UncertainDoubleField f, double correlation) {
            return addOrSubtract(value,uncertainty,f.value(),f.uncertainty(),false,correlation); //magic number
        }

        @Override
        public UncertainDoubleField multiply(UncertainDoubleField f) {
            return multiply(f,0); //magic number
        }

        public UncertainDoubleField multiply(UncertainDoubleField f, double correlation) {
            return multiplyOrDivide(value,uncertainty,f.value(),f.uncertainty(),true,correlation); //magic number
        }

        private static UncertainDoubleField multiplyOrDivide(double value_a,
                                                   double sigma_a, 
                                                   double value_b,
                                                   double sigma_b,
                                                   boolean multiply,
                                                   double correlation) {
            double v = value_a * (multiply ? value_b : 1/value_b);
            double u = Math.abs(v)*Math.sqrt((sigma_a*sigma_a)/(value_a*value_a) 
                    + (sigma_b*sigma_b)/(value_b*value_b)
                    + (multiply ? 2 : -2)*correlation*sigma_a*sigma_b/(value_a*value_b));
            return new UncertainDoubleField(v,u);
        }

        @Override
        public UncertainDoubleField divide(UncertainDoubleField f) {
            return divide(f,0); //magic number
        }

        public UncertainDoubleField divide(UncertainDoubleField f, double correlation) {
            return multiplyOrDivide(value,uncertainty,f.value(),f.uncertainty(),false,correlation); //magic number
        }

        @Override
        public UncertainDoubleField power(UncertainDoubleField exponent) throws ArithmeticException {
            return power(exponent,0); //magic number

        }

        public UncertainDoubleField power(UncertainDoubleField exponent, double correlation) throws ArithmeticException {
            if (exponent.isEqualTo(ZERO))
                return ONE;
            if (exponent.isEqualTo(ONE))
                return this;
            double v = Math.pow(value,exponent.value());
            double t1 = exponent.value()*exponent.value()*uncertainty*uncertainty/(value*value);
            double lnA = Math.log(value);
            double t2 = lnA*lnA*exponent.uncertainty()*exponent.uncertainty();
            double t3 = 2*exponent.value()*lnA*correlation*uncertainty*exponent.uncertainty()/value;
            double u = Math.abs(v)*Math.sqrt(t1 + t2 + t3);
            return new UncertainDoubleField(v,u);
        }

        @Override
        public UncertainDoubleField power(Exponent exponent) {
            if (exponent.isEqualTo(Exponents.ZERO))
                return ONE;
            if (exponent.isEqualTo(Exponents.ONE))
                return this;
            return powerNoCheck((double)exponent.numerator()/exponent.denominator());
        }

        private UncertainDoubleField powerNoCheck(double exponent) {
            double v = Math.pow(value,exponent);
            double u = Math.abs(v*exponent*uncertainty/value);
            return new UncertainDoubleField(v,u);
        }

        public UncertainDoubleField power(double exponent) {
            if (exponent == 0)
                return ONE;
            if (exponent == 1)
                return this;
            return powerNoCheck(exponent);
        } 

        @Override
        public UncertainDoubleField logarithm(UncertainDoubleField base) throws ArithmeticException {
            UncertainDoubleField ln = ln();
            if (base.isEqualTo(E))
                return ln;
            double lnB = Math.log(base.value());
            double v = ln.value()/lnB;
            double u = ln.uncertainty()/lnB;
            return new UncertainDoubleField(v,u);
        }

        private UncertainDoubleField ln() {
            double v = Math.log(value);
            double u = Math.abs(uncertainty/value);
            return new UncertainDoubleField(v,u);
        }

        @Override
        public int compareTo(UncertainDoubleField o) {
            int i = Double.compare(value,o.value());
            return i != 0 ? i : Double.compare(uncertainty,o.uncertainty());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof UncertainDoubleField))
                return false;
            UncertainDoubleField o2 = (UncertainDoubleField)o;
            return value == o2.value() && uncertainty == o2.uncertainty();
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash
            = 67 * hash + (int)(Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
            hash
            = 67 * hash + (int)(Double.doubleToLongBits(this.uncertainty) ^ (Double.doubleToLongBits(this.uncertainty) >>> 32));
            return hash;
        }

        @Override
        public final String toString() {
            return toString(0); //magic number
        }

        public final String toString(int uncertaintyPrecision) {
            String v,u;
            if (uncertaintyPrecision > 0) {
                if (uncertainty != 0) {
                    String uFormat = "%1$." + Integer.toString(uncertaintyPrecision) + "g";
                    u = String.format(uFormat,uncertainty);
                    int l = (int)(uncertainty < 1.0 ? 
                            Math.floor(Math.log10(uncertainty)) :
                            Math.ceil(Math.log10(uncertainty))) - uncertaintyPrecision + 1;
                    double absV = Math.abs(value);
                    int s = (int)(absV < 1.0 ?
                            Math.floor(Math.log10(absV)) :
                            Math.ceil(Math.log10(absV))) - l;
                    String vFormat = "%1$." + Integer.toString(s) + "g";
                    v = String.format(vFormat,value);
                } else {
                    v = Double.toString(value);
                    u = "0";
                }
            } else {
                v = Double.toString(value);
                u = Double.toString(uncertainty);
            }
            return v + "(" + u + ")";
        }

        private static DoubleField apply(DoubleField[] values, ToDoubleFunction<double[]> function) {
            Objects.nonNull(values);
            if (values.length == 0)
                throw new IllegalArgumentException();
            return DoubleField.of(function.applyAsDouble(
                    Stream.of(values)
                            .mapToDouble((f) -> f.value())
                            .toArray()));
        }

        public static final DoubleField avg(DoubleField... values) {
            return apply(values,UncertainDoubleField::avgNoCheck);
        }

        private static double avgNoCheck(double... values) {
            return DoubleStream.of(values).average().getAsDouble();
        }

        public static final DoubleField devsq(DoubleField... values) {
            return apply(values,UncertainDoubleField::devsqNoCheck);
        }

        private static double devsqNoCheck(double... values) {
            double avg = avgNoCheck(values);
            return DoubleStream.of(values).map((d) -> (d - avg)*(d - avg)).sum();
        }

        public static final DoubleField var(DoubleField... values) {
            return apply(values,UncertainDoubleField::varNoCheck);
        }

        private static double varNoCheck(double... values) {
            int nm1 = values.length - 1;
            return devsqNoCheck(values)/nm1;
        }

        public static final DoubleField varm(DoubleField... values) {
            return apply(values,UncertainDoubleField::varmNoCheck);
        }

        private static double varmNoCheck(double... values) {
            int n = values.length;
            return varNoCheck(values)/n;
        }

        public static final DoubleField stdev(DoubleField... values) {
            return apply(values,UncertainDoubleField::stdevNoCheck);
        }

        private static double stdevNoCheck(double... values) {
            return Math.sqrt(varNoCheck(values));
        }

        public static final DoubleField stdevm(DoubleField... values) {
            return apply(values,UncertainDoubleField::stdevmNoCheck);
        }

        private static double stdevmNoCheck(double... values) {
            return Math.sqrt(varmNoCheck(values));
        }

        private static DoubleField apply(DoubleField[] q, DoubleField[] r, ToDoubleBiFunction<double[],double[]> function) {
            Objects.nonNull(q);
            Objects.nonNull(r);
            if (!(q.length == r.length && q.length != 0))
                throw new IllegalArgumentException();
            return DoubleField.of(function.applyAsDouble(
                    Stream.of(q)
                            .mapToDouble((f) -> f.value())
                            .toArray(),
                    Stream.of(r)
                            .mapToDouble((f) -> f.value())
                            .toArray()));
        } 

        public static final DoubleField covar(DoubleField[] q, DoubleField[] r) {
            return apply(q,r,UncertainDoubleField::covarNoCheck);
        }

        private static double covarNoCheck(double[] q, double[] r) {
            double qAvg = avgNoCheck(q);
            double rAvg = avgNoCheck(r);
            int n = q.length;
            double[] devq = DoubleStream.of(q).map((i) -> i - qAvg).toArray();
            double[] devr = DoubleStream.of(r).map((i) -> i - rAvg).toArray();
            double[] cs = new double[n];
            Arrays.setAll(cs,(i) -> devq[i]*devr[i]);
            return DoubleStream.of(cs).sum()/n/(n-1);
        }

        public static final DoubleField correl(DoubleField[] q, DoubleField[] r) {
            return apply(q,r,UncertainDoubleField::correlNoCheck);
        }

        private static double correlNoCheck(double[] q, double[] r) {
            return covarNoCheck(q,r)/stdevmNoCheck(q)/stdevmNoCheck(r);
        }

        public static final class Measure implements com.jamw.jmud.Measure<UncertainDoubleField> {

            public static final UncertainDoubleField.Measure of(double value,double uncertainty,Unit unit) {
                return of(UncertainDoubleField.of(value,uncertainty),unit);
            }

            public static final UncertainDoubleField.Measure of(DoubleField value,DoubleField uncertainty,Unit unit) {
                return of(value.value(),uncertainty.value(),unit);
            }

            public static final UncertainDoubleField.Measure of(UncertainDoubleField value,Unit unit) {
                return of(Expressions.take(value,unit));
            }

            public static final UncertainDoubleField.Measure of(com.jamw.jmud.Measure<DoubleField> value,com.jamw.jmud.Measure<DoubleField> uncertainty) {
                return of(value.as(uncertainty.getUnit()).getField(),uncertainty.getField(),uncertainty.getUnit());
            }

            public static final UncertainDoubleField.Measure of(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return new Measure(measure);
            }

            public static final UncertainDoubleField.Measure abs(Expression expression) {
                return abs(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure abs(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return of(Math.abs(measure.getField().value()),measure.getField().uncertainty(),measure.getUnit());
            }

            public static final UncertainDoubleField.Measure ceil(Expression expression) {
                return ceil(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure ceil(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return of(Math.ceil(measure.getField().value()),measure.getField().uncertainty(),measure.getUnit());
            }

            public static final UncertainDoubleField.Measure floor(Expression expression) {
                return floor(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure floor(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return of(Math.floor(measure.getField().value()),measure.getField().uncertainty(),measure.getUnit());
            }

            public static final UncertainDoubleField signum(Expression expression) {
                return signum(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField signum(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return Math.signum(measure.getField().value()) == 0 ?
                       ZERO :
                       Math.signum(measure.getField().value()) > 0 ?
                       ONE :
                       NEGATIVE_ONE;
            }

            public static final UncertainDoubleField.Measure cos(Expression expression) {
                return cos(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure cos(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                com.jamw.jmud.Measure<UncertainDoubleField> m = measure.as(Units.RADIAN);
                double v = Math.cos(m.getField().value());
                double u = Math.abs(Math.sin(m.getField().value())*m.getField().uncertainty());
                return of(v,u,Units.UNITLESS);
            }

            public static final UncertainDoubleField.Measure sin(Expression expression) {
                return sin(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure sin(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                com.jamw.jmud.Measure<UncertainDoubleField> m = measure.as(Units.RADIAN);
                double v = Math.sin(m.getField().value());
                double u = Math.abs(Math.cos(m.getField().value())*m.getField().uncertainty());
                return of(v,u,Units.UNITLESS);
            }

            public static final UncertainDoubleField.Measure tan(Expression expression) {
                return tan(expression.using(UncertainDoubleField.factory()));
            }

            public static final UncertainDoubleField.Measure tan(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                com.jamw.jmud.Measure<UncertainDoubleField> m = measure.as(Units.RADIAN);
                double v = Math.tan(m.getField().value());
                double cos = Math.cos(m.getField().value());
                double u = m.getField().uncertainty()/(cos*cos);
                return of(v,u,Units.UNITLESS);
            }

            private final com.jamw.jmud.Measure<UncertainDoubleField> measure;

            private Measure(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                this.measure = measure;
            }

            @Override
            public UncertainDoubleField getField() {
                return measure.getField();
            }
            
            public DoubleField.Measure value() {
                return DoubleField.Measure.of(measure.getField().value(),measure.getUnit());
            }
            
            public DoubleField.Measure uncertainty() {
                return DoubleField.Measure.of(measure.getField().uncertainty(),measure.getUnit());
            }

            @Override
            public Unit getUnit() {
                return measure.getUnit();

            }

            @Override
            public int compareTo(com.jamw.jmud.Measure<UncertainDoubleField> o) throws IncommensurableDimensionException {
                return measure.compareTo(o);
            }

            @Override
            public UncertainDoubleField.Measure add(int value,Unit unit)
                    throws IncommensurableDimensionException {
                return of(measure.add(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure add(String value,Unit unit)
                    throws IncommensurableDimensionException {
                return of(measure.add(value,unit));
            }
            
            @Override
            public UncertainDoubleField.Measure add(Scalar value,Unit unit)
                    throws IncommensurableDimensionException {
                return of(measure.add(value,unit));
            }
            
            @Override
            public UncertainDoubleField.Measure add(UncertainDoubleField value, Unit unit) 
                    throws IncommensurableDimensionException {
                return of(measure.add(value,unit));
            }
            
            @Override
            public UncertainDoubleField.Measure add(com.jamw.jmud.Measure<UncertainDoubleField> measure)
                    throws IncommensurableDimensionException {
                return of(this.measure.add(measure));
            }
            
            @Override
            public UncertainDoubleField.Measure add(Expression expression)
                    throws IncommensurableDimensionException {
                return of(measure.add(expression));
            }
            
            public UncertainDoubleField.Measure add(String value,Unit unit,String correlation)
                    throws IncommensurableDimensionException {
                return add(UncertainDoubleField.factory().of(value),unit,DoubleField.factory().of(correlation));
            }

            public UncertainDoubleField.Measure add(Scalar value,Unit unit,Scalar correlation)
                    throws IncommensurableDimensionException {
                return add(value.using(UncertainDoubleField.factory()),unit,correlation.using(DoubleField.factory()));
            }

            public UncertainDoubleField.Measure add(UncertainDoubleField value, Unit unit,DoubleField correlation) 
                    throws IncommensurableDimensionException {
                return add(of(value,unit),correlation);
            }

            public UncertainDoubleField.Measure add(com.jamw.jmud.Measure<UncertainDoubleField> measure, DoubleField correlation)
                    throws IncommensurableDimensionException {
                UncertainDoubleField v = getField().add(measure.as(getUnit()).getField(),correlation.value());
                return of(v,getUnit());
            }

            public UncertainDoubleField.Measure add(Expression expression,Scalar correlation)
                    throws IncommensurableDimensionException {
                return add(expression.using(UncertainDoubleField.factory()),correlation.using(DoubleField.factory()));
            }
            
            public UncertainDoubleField.Measure add(Expression expression,Expression correlation)
                    throws IncommensurableDimensionException {
                return add(expression.using(UncertainDoubleField.factory()),
                           correlation.using(DoubleField.factory()).as(Units.UNITLESS).getField());
            }

            @Override
            public UncertainDoubleField.Measure subtract(int value,Unit unit)
                    throws IncommensurableDimensionException {
                return of(measure.subtract(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure subtract(String value, Unit unit) 
                    throws IncommensurableDimensionException {
                return of(measure.subtract(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure subtract(Scalar value, Unit unit) 
                    throws IncommensurableDimensionException {
                return of(measure.subtract(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure subtract(UncertainDoubleField value, Unit unit) 
                    throws IncommensurableDimensionException {
                return of(measure.subtract(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure subtract(com.jamw.jmud.Measure<UncertainDoubleField> measure)
                    throws IncommensurableDimensionException {
                return of(this.measure.subtract(measure));
            }

            @Override
            public UncertainDoubleField.Measure subtract(Expression expression)
                    throws IncommensurableDimensionException {
                return of(measure.subtract(expression));
            }

            public UncertainDoubleField.Measure subtract(String value,Unit unit,String correlation)
                    throws IncommensurableDimensionException {
                return subtract(UncertainDoubleField.factory().of(value),unit,DoubleField.factory().of(correlation));
            }

            public UncertainDoubleField.Measure subtract(Scalar value,Unit unit,Scalar correlation)
                    throws IncommensurableDimensionException {
                return subtract(value.using(UncertainDoubleField.factory()),unit,correlation.using(DoubleField.factory()));
            }

            public UncertainDoubleField.Measure subtract(UncertainDoubleField value, Unit unit,DoubleField correlation) 
                    throws IncommensurableDimensionException {
                return subtract(of(value,unit),correlation);
            }

            public UncertainDoubleField.Measure subtract(com.jamw.jmud.Measure<UncertainDoubleField> measure, DoubleField correlation)
                    throws IncommensurableDimensionException {
                UncertainDoubleField v = getField().subtract(measure.as(getUnit()).getField(),correlation.value());
                return of(v,getUnit());
            }

            public UncertainDoubleField.Measure subtract(Expression expression,Scalar correlation)
                    throws IncommensurableDimensionException {
                return subtract(expression.using(UncertainDoubleField.factory()),correlation.using(DoubleField.factory()));
            }
            
            public UncertainDoubleField.Measure subtract(Expression expression,Expression correlation)
                    throws IncommensurableDimensionException {
                return subtract(expression.using(UncertainDoubleField.factory()),
                           correlation.using(DoubleField.factory()).as(Units.UNITLESS).getField());
            }
            
            @Override
            public UncertainDoubleField.Measure multiply(int scalar) {
                return of(measure.multiply(scalar));
            }

            @Override
            public UncertainDoubleField.Measure multiply(String scalar) {
                return of(measure.multiply(scalar));
            }

            @Override
            public UncertainDoubleField.Measure multiply(Scalar scalar) {
                return of(measure.multiply(scalar));
            }

            @Override
            public UncertainDoubleField.Measure multiply(UncertainDoubleField scalar) {
                return of(measure.multiply(scalar));
            }

            @Override
            public UncertainDoubleField.Measure multiply(int value,Unit unit) {
                return of(measure.multiply(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure multiply(String value, Unit unit) {
                return of(measure.multiply(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure multiply(Scalar value, Unit unit) {
                return of(measure.multiply(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure multiply(UncertainDoubleField value, Unit unit) {
                return of(measure.multiply(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure multiply(com.jamw.jmud.Measure<UncertainDoubleField> measure) {
                return of(this.measure.multiply(measure));
            }

            @Override
            public UncertainDoubleField.Measure multiply(Expression expression) {
                return of(measure.multiply(expression));
            }
            
            public UncertainDoubleField.Measure multiply(String value,Unit unit,String correlation)
                    throws IncommensurableDimensionException {
                return multiply(UncertainDoubleField.factory().of(value),unit,DoubleField.factory().of(correlation));
            }

            public UncertainDoubleField.Measure multiply(Scalar value,Unit unit,Scalar correlation)
                    throws IncommensurableDimensionException {
                return multiply(value.using(UncertainDoubleField.factory()),unit,correlation.using(DoubleField.factory()));
            }

            public UncertainDoubleField.Measure multiply(UncertainDoubleField value, Unit unit,DoubleField correlation) 
                    throws IncommensurableDimensionException {
                return multiply(of(value,unit),correlation);
            }

            public UncertainDoubleField.Measure multiply(com.jamw.jmud.Measure<UncertainDoubleField> measure, DoubleField correlation)
                    throws IncommensurableDimensionException {
                UncertainDoubleField v = getField().multiply(measure.getField(),correlation.value());
                Unit u = Units.newUnit().as(getUnit()).multiply(measure.getUnit()).create();
                return of(v,u);
            }

            public UncertainDoubleField.Measure multiply(Expression expression,Scalar correlation)
                    throws IncommensurableDimensionException {
                return multiply(expression.using(UncertainDoubleField.factory()),correlation.using(DoubleField.factory()));
            }
            
            public UncertainDoubleField.Measure multiply(Expression expression,Expression correlation)
                    throws IncommensurableDimensionException {
                return multiply(expression.using(UncertainDoubleField.factory()),
                           correlation.using(DoubleField.factory()).as(Units.UNITLESS).getField());
            }

            @Override
            public UncertainDoubleField.Measure divide(int scalar) 
                    throws ArithmeticException {
                return of(measure.divide(scalar));
            }

            @Override
            public UncertainDoubleField.Measure divide(String scalar) 
                    throws ArithmeticException {
                return of(measure.divide(scalar));
            }

            @Override
            public UncertainDoubleField.Measure divide(Scalar scalar) 
                    throws ArithmeticException {
                return of(measure.divide(scalar));
            }

            @Override
            public UncertainDoubleField.Measure divide(UncertainDoubleField scalar)
                    throws ArithmeticException {
                return of(measure.divide(scalar));
            }

            @Override
            public UncertainDoubleField.Measure divide(int value,Unit unit)
                    throws ArithmeticException {
                return of(measure.divide(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure divide(String value,Unit unit)
                    throws ArithmeticException {
                return of(measure.divide(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure divide(Scalar value,Unit unit)
                    throws ArithmeticException {
                return of(measure.divide(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure divide(UncertainDoubleField value, Unit unit)
                    throws ArithmeticException {
                return of(measure.divide(value,unit));
            }

            @Override
            public UncertainDoubleField.Measure divide(com.jamw.jmud.Measure<UncertainDoubleField> measure)
                    throws ArithmeticException {
                return of(this.measure.divide(measure));
            }

            @Override
            public UncertainDoubleField.Measure divide(Expression expression)
                    throws ArithmeticException {
                return of(measure.divide(expression));
            }
            
            public UncertainDoubleField.Measure divide(String value,Unit unit,String correlation)
                    throws IncommensurableDimensionException {
                return divide(UncertainDoubleField.factory().of(value),unit,DoubleField.factory().of(correlation));
            }

            public UncertainDoubleField.Measure divide(Scalar value,Unit unit,Scalar correlation)
                    throws IncommensurableDimensionException {
                return divide(value.using(UncertainDoubleField.factory()),unit,correlation.using(DoubleField.factory()));
            }

            public UncertainDoubleField.Measure divide(UncertainDoubleField value, Unit unit,DoubleField correlation) 
                    throws IncommensurableDimensionException {
                return divide(of(value,unit),correlation);
            }

            public UncertainDoubleField.Measure divide(com.jamw.jmud.Measure<UncertainDoubleField> measure, DoubleField correlation)
                    throws IncommensurableDimensionException {
                UncertainDoubleField v = getField().divide(measure.getField(),correlation.value());
                Unit u = Units.newUnit().as(getUnit()).divide(measure.getUnit()).create();
                return of(v,u);
            }

            public UncertainDoubleField.Measure divide(Expression expression,Scalar correlation)
                    throws IncommensurableDimensionException {
                return divide(expression.using(UncertainDoubleField.factory()),correlation.using(DoubleField.factory()));
            }
            
            public UncertainDoubleField.Measure divide(Expression expression,Expression correlation)
                    throws IncommensurableDimensionException {
                return divide(expression.using(UncertainDoubleField.factory()),
                           correlation.using(DoubleField.factory()).as(Units.UNITLESS).getField());
            }

            @Override
            public UncertainDoubleField.Measure power(Exponent exponent)
                    throws ArithmeticException {
                return of(measure.power(exponent));   
            }

            @Override
            public UncertainDoubleField.Measure as(Unit unit)
                    throws IncommensurableDimensionException {
                return of(measure.as(unit));
            }
            
            @Override
            public String toString() {
                return measure.toString();
            }
            
            public String toString(int uncertaintyPrecision) {
                return measure.getField().toString(uncertaintyPrecision) + " " + measure.getUnit().getSymbol();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o)
                    return true;
                if (!(o instanceof UncertainDoubleField.Measure))
                    return false;
                UncertainDoubleField.Measure om = (UncertainDoubleField.Measure)o;
                return this.getUnit().equals(om.getUnit())
                        && this.getField().isEqualTo(om.getField());
            }

            @Override
            public int hashCode() {
                int hash = 3;
                hash = 37 * hash + Objects.hashCode(measure.getField().value());
                hash = 37 * hash + Objects.hashCode(measure.getUnit());
                return hash;
            }

            public UncertainDoubleField.Measure abs() {
                return abs(this);
            }

            public UncertainDoubleField.Measure ceil() {
                return ceil(this);
            }

            public UncertainDoubleField.Measure floor() {
                return floor(this);
            }

            private static void checkArray(Object[] array) {
                Objects.nonNull(array);
                if (array.length == 0) 
                    throw new IllegalArgumentException();
            }

            public static final UncertainDoubleField.Measure avg(DoubleField.Measure... values) {
                checkArray(values);
                Unit u = values[0].getUnit(); //magic number
                double[] s = Stream.of(values).mapToDouble(m ->
                        m.as(u).getField().value()).toArray();
                return of(UncertainDoubleField.avgNoCheck(s),
                          UncertainDoubleField.stdevmNoCheck(s),
                          u);
            }

            private static void checkArray(Object[] a1, Object[] a2) {
                Objects.nonNull(a1);
                Objects.nonNull(a2);
                if (!(a1.length == a2.length && a1.length != 0))
                    throw new IllegalArgumentException();
            }
            
            public static final DoubleField correl(DoubleField.Measure[] q, DoubleField.Measure[] r) {
                checkArray(q,r);
                Unit qu = q[0].getUnit(); //magic number
                Unit ru = r[0].getUnit(); //magic number
                double[] qs = Stream.of(q).mapToDouble(m ->
                        m.as(qu).getField().value()).toArray();
                double[] rs = Stream.of(r).mapToDouble(m ->
                        m.as(ru).getField().value()).toArray();
                return DoubleField.of(UncertainDoubleField.correlNoCheck(qs,rs));
            }
            
            @SuppressWarnings("unchecked")
            public static final <Q extends Enum<Q>> UncertainDoubleField.Measure 
            evaluate(Function<Observation<Q,DoubleField>,com.jamw.jmud.Measure<DoubleField>> function,Observation<Q,DoubleField>[] observations) {
                checkArray(observations);
                Q[] quantities = observations[0].quantities(); //magic number
                int numQuantities = quantities.length;
                int numObservations = observations.length;
                var obs = new DoubleField.Measure[numQuantities][numObservations];
                for (Q q : quantities) 
                    obs[q.ordinal()] = Stream.of(observations).map(o -> o.valueOf(q)).toArray(DoubleField.Measure[]::new);
                
                var avg = new UncertainDoubleField.Measure[numQuantities];
                for (Q q : quantities) 
                    avg[q.ordinal()] = UncertainDoubleField.Measure.avg(obs[q.ordinal()]);
                Observation<Q,DoubleField> a = new Observation<>() {
                    private final DoubleField.Measure[] measures;
                    {
                        measures = new DoubleField.Measure[numQuantities];
                        for (Q q : quantities)
                            measures[q.ordinal()] = avg[q.ordinal()].value();
                    }
                    @Override public Q[] quantities() {return quantities;}
                    @Override public com.jamw.jmud.Measure<DoubleField> 
                    valueOf(Q observable) {return measures[observable.ordinal()];}
                };
                com.jamw.jmud.Measure<DoubleField> v = function.apply(a);
                var corr = new CorrelationMatrix(numQuantities);
                for (Q r : quantities)
                    for (Q c : quantities)
                        if (c.ordinal() > r.ordinal())
                            corr.set(r.ordinal(),c.ordinal(),UncertainDoubleField.Measure.correl(obs[r.ordinal()],obs[c.ordinal()]));
                com.jamw.jmud.Measure<DoubleField>[] Z = new DoubleField.Measure[numQuantities];
                for (Q q : quantities) {
                    DoubleField.Measure delta = avg[q.ordinal()].uncertainty();
                    Observation<Q,DoubleField> plus = createObservation(quantities,q,delta,avg);
                    Observation<Q,DoubleField> minus = createObservation(quantities,q,delta.multiply(-1),avg);
                    Z[q.ordinal()] = function.apply(plus).subtract(function.apply(minus)).divide(2);
                }
                Unit varUnit = Units.newUnit().as(v.getUnit(),Exponents.SQUARED).create();
                DoubleField.Measure u = DoubleField.Measure.of(DoubleField.ZERO,varUnit);
                for (Q r : quantities)
                    for (Q c : quantities) 
                        u = u.add(Z[r.ordinal()].multiply(Z[c.ordinal()]).multiply(corr.get(r.ordinal(),c.ordinal())));
                u = u.power(Exponents.SQUARE_ROOT).as(v.getUnit());
                return of(v.getField(),u.getField(),v.getUnit());
            }
            
            private static <Q extends Enum<Q>> Observation<Q,DoubleField> 
            createObservation(Q[] quantities, Q varies, DoubleField.Measure amt, UncertainDoubleField.Measure[] measures) {
                return new Observation<>() {
                    private final DoubleField.Measure[] m;
                    {
                        m = new DoubleField.Measure[measures.length];
                        for (int i = 0; i< m.length; i++)
                            m[i] = measures[i].value();
                        m[varies.ordinal()] = m[varies.ordinal()].add(amt);
                    }
                    @Override public Q[] quantities() {return quantities;}
                    @Override public com.jamw.jmud.Measure<DoubleField> 
                    valueOf(Q observable) {return m[observable.ordinal()];}
                };
            }
            
            /**
             * Adapted from Matrix Toolkits Java by Bjørn-Ove Heimsund.
             * @param <F> 
             */
            private static final class CorrelationMatrix {
                private final int size;
                private DoubleField[] data;
                
                @SuppressWarnings("unchecked")
                public CorrelationMatrix(int size) {
                    if (size <=0 ) 
                        throw new IllegalArgumentException();
                    this.size = size;
                    data = new DoubleField[(size * size + size) / 2];
                    for (int i=0; i<size; i++) 
                        set(i,i,DoubleField.of(1.)); //magic number
                }
                
                public DoubleField get(int row, int column) {
                    if (row > column)
                        return get(column,row);
                    return data[getIndex(row,column)];
                }

                public void set(int row, int column, DoubleField value) {
                    if (row > column)
                        set(column,row,value);
                    data[getIndex(row,column)] = value;
                }
                
                private int getIndex(int row, int column) {
                    check(row, column);
                    return row + (column + 1) * column / 2;
                }
                
                private void check(int row, int column) {
                    if (row < 0 || column < 0 || row >= size || column >= size)
                        throw new IndexOutOfBoundsException();
                }
            }
        }
    }
    
    static interface Observation<Q extends Enum<Q>,F extends Field<F>> {
        Q[] quantities();
        com.jamw.jmud.Measure<F> valueOf(Q observable);
    }

    private static final Unit mA = Units.milli(Units.AMPERE);
    private static final Unit V = Units.newUnit().as(Units.VOLT).withSymbol("V").create();
    private static final Unit rad = Units.RADIAN;
    private static final Unit ohm = Units.newUnit().as(Units.OHM).withSymbol("\u03A9").create();
    
    private static enum InputObservables {
        V,I,PHI;
    }
    
    private static final class InputObservation implements Observation<InputObservables,DoubleField> {
        
        @Override
        public InputObservables[] quantities() {
            return InputObservables.values();
        }
        
        @Override
        public DoubleField.Measure valueOf(InputObservables o) {
            switch (o) {
                case V: return V();
                case I: return I();
                default: return PHI();
            }         
        }
        
        private final DoubleField.Measure V,I,PHI;
        
        InputObservation(DoubleField.Measure V,
                           DoubleField.Measure I,
                           DoubleField.Measure PHI) {
            this.V = V;
            this.I = I;
            this.PHI = PHI;
        }
        
        public DoubleField.Measure V() {
            return V;
        }
        
        public DoubleField.Measure I() {
            return I;
        }
        
        public DoubleField.Measure PHI() {
            return PHI;
        }
    }
    
    private static enum OutputObservables {
        R,X,Z;
    }
    
    private static final class OutputObservation implements Observation<OutputObservables,DoubleField> {
        
        @Override
        public OutputObservables[] quantities() {
            return OutputObservables.values();
        }
        
        @Override
        public com.jamw.jmud.Measure<DoubleField> valueOf(OutputObservables o) {
            switch (o) {
                case R: return R();
                case X: return X();
                default: return Z();
            }         
        }
        
        private final com.jamw.jmud.Measure<DoubleField> R,X,Z;
        
        OutputObservation(com.jamw.jmud.Measure<DoubleField> R,
                          com.jamw.jmud.Measure<DoubleField> X,
                          com.jamw.jmud.Measure<DoubleField> Z) {
            this.R = R;
            this.X = X;
            this.Z = Z;
        }
        
        public com.jamw.jmud.Measure<DoubleField> R() {
            return R;
        }
        
        public com.jamw.jmud.Measure<DoubleField> X() {
            return X;
        }
        
        public com.jamw.jmud.Measure<DoubleField> Z() {
            return Z;
        }
    }
    
    @Test
    public void example() {
        InputObservation[] inputObservations = new InputObservation[]{
            new InputObservation(DoubleField.Measure.of(5.007,V),
                            DoubleField.Measure.of(19.663,mA),
                            DoubleField.Measure.of(1.0456,rad)),
            new InputObservation(DoubleField.Measure.of(4.994,V),
                            DoubleField.Measure.of(19.639,mA),
                            DoubleField.Measure.of(1.0438,rad)),
            new InputObservation(DoubleField.Measure.of(5.005,V),
                            DoubleField.Measure.of(19.640,mA),
                            DoubleField.Measure.of(1.0468,rad)),
            new InputObservation(DoubleField.Measure.of(4.990,V),
                            DoubleField.Measure.of(19.685,mA),
                            DoubleField.Measure.of(1.0428,rad)),
            new InputObservation(DoubleField.Measure.of(4.999,V),
                            DoubleField.Measure.of(19.678,mA),
                            DoubleField.Measure.of(1.0433,rad)),
        };
        DoubleField.Measure[] oV = Stream.of(inputObservations).map(o -> o.V()).toArray(DoubleField.Measure[]::new);
        DoubleField.Measure[] oI = Stream.of(inputObservations).map(o -> o.I()).toArray(DoubleField.Measure[]::new);
        DoubleField.Measure[] oPHI = Stream.of(inputObservations).map(o -> o.PHI()).toArray(DoubleField.Measure[]::new);
        
        var V_avg = UncertainDoubleField.Measure.avg(oV);
        var I_avg = UncertainDoubleField.Measure.avg(oI);
        var PHI_avg = UncertainDoubleField.Measure.avg(oPHI);
        String VinVolts = "4.9990(0.0032)";
        assertTrue(V_avg.as(V).getField().toString(2).equals(VinVolts));
        String IinMilliAmps = "19.6610(0.0095)";
        assertTrue(I_avg.as(mA).getField().toString(2).equals(IinMilliAmps));
        String phiInRad = "1.04446(0.00075)";
        assertTrue(PHI_avg.as(rad).getField().toString(2).equals(phiInRad));
        
        var r_V_I = UncertainDoubleField.Measure.correl(oV,oI);
        var r_V_PHI = UncertainDoubleField.Measure.correl(oV,oPHI);
        var r_I_PHI = UncertainDoubleField.Measure.correl(oI,oPHI);
        String corr_V_I = "-0.36";
        assertTrue(String.format("%1$.2g",r_V_I.value()).equals(corr_V_I));
        String corr_V_PHI = "0.86";
        assertTrue(String.format("%1$.2g",r_V_PHI.value()).equals(corr_V_PHI));
        String corr_I_PHI = "-0.65";
        assertTrue(String.format("%1$.2g",r_I_PHI.value()).equals(corr_I_PHI));
        
        Function<Observation<InputObservables,DoubleField>,com.jamw.jmud.Measure<DoubleField>> R_func = 
        o -> o.valueOf(InputObservables.V)
                .divide(o.valueOf(InputObservables.I))
                .multiply(DoubleField.Measure.cos(o.valueOf(InputObservables.PHI)));
        UncertainDoubleField.Measure R = UncertainDoubleField.Measure.evaluate(
                R_func,inputObservations);
        Function<Observation<InputObservables,DoubleField>,com.jamw.jmud.Measure<DoubleField>> X_func = 
        o -> o.valueOf(InputObservables.V)
                .divide(o.valueOf(InputObservables.I))
                .multiply(DoubleField.Measure.sin(o.valueOf(InputObservables.PHI)));
        UncertainDoubleField.Measure X = UncertainDoubleField.Measure.evaluate(
                X_func,inputObservations);
        UncertainDoubleField.Measure Z = V_avg.divide(I_avg,r_V_I);
        String RinOhm = "127.732(0.071)";
        assertTrue(R.as(ohm).getField().toString(2).equals(RinOhm));
        String XinOhm = "219.847(0.296)";
        assertTrue(X.as(ohm).getField().toString(3).equals(XinOhm));
        String ZinOhm = "254.260(0.236)";
        assertTrue(Z.as(ohm).getField().toString(3).equals(ZinOhm));
        
        OutputObservation[] outputObservations = new OutputObservation[inputObservations.length];
        for (int i = 0; i< inputObservations.length; i++) {
            outputObservations[i] = new OutputObservation(
                    R_func.apply(inputObservations[i]),
                    X_func.apply(inputObservations[i]),
                    inputObservations[i].V().divide(inputObservations[i].I()));
        }
        DoubleField.Measure[] oR = Stream.of(outputObservations).map(o -> o.R()).toArray(DoubleField.Measure[]::new);
        DoubleField.Measure[] oX = Stream.of(outputObservations).map(o -> o.X()).toArray(DoubleField.Measure[]::new);
        DoubleField.Measure[] oZ = Stream.of(outputObservations).map(o -> o.Z()).toArray(DoubleField.Measure[]::new);
        
        var r_R_X = UncertainDoubleField.Measure.correl(oR,oX);
        var r_R_Z = UncertainDoubleField.Measure.correl(oR,oZ);
        var r_X_Z = UncertainDoubleField.Measure.correl(oX,oZ);
        String corr_R_I = "-0.588";
        assertTrue(String.format("%1$.3g",r_R_X.value()).equals(corr_R_I));
        String corr_R_Z = "-0.485";
        assertTrue(String.format("%1$.3g",r_R_Z.value()).equals(corr_R_Z));
        String corr_X_Z = "0.993";
        assertTrue(String.format("%1$.3g",r_X_Z.value()).equals(corr_X_Z));
    }
}
