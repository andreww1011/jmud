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
/**
 * A Java library for creating and manipulating dimensional units of 
 * measurement.
 * 
 * <p>jmud - (J)ava (M)easures, (U)nits, and (D)imensions.<br>
 * 
 * <h2>Usage</h2>
 * <pre><code>  Unit kilometer = Units.kilo(Units.METER);
  Unit kilometerPerHour = Units.newUnit()  
      .ofDimension(Dimensions.VELOCITY)  
      .as(kilometer).divide(Units.HOUR)  
      .withSymbol("kph").create();
  Unit mile = Units.newUnit()  
      .ofDimension(Dimensions.LENGTH)  
      .asExactly(5280).ofA(Units.FOOT)  
      .create();

  Expression distance = Expressions.take(7, mile);
  Expression time = Expressions.take("16.09344", Units.MINUTE);
  Expression speed = distance.divide(time);
  
  Measure&lt;DoubleField&gt; m = speed.using(DoubleField.factory());

  System.out.println(m.as(kilometerPerHour).toString());  //prints "42.0 kph"</code></pre>
 * <h2>Getting Started</h2>
 * <table>
 * <tr>
 * <td>{@link Constants}</td><td></td><td>Abstract class of common universal constants.</td>
 * </tr>
 * <tr>
 * <td>{@link Dimensions}</td><td></td><td>Abstract class of commonly used {@linkplain Dimension dimension}s and 
 * for creating new dimensions.</td>
 * </tr>
 * <tr>
 * <td>{@link Exponents}</td><td></td><td>Abstract class of commonly used rational numbers
 * used as {@linkplain Exponent exponent}s in dimensional analysis and for creating new exponents.</td>
 * </tr>
 * <tr>
 * <td>{@link Expressions}</td><td></td><td>Abstract class for creating {@linkplain Measure measure}s, 
 * {@linkplain Expression expression}s, and {@linkplain Scalar scalar}s.</td>
 * </tr>
 * <tr>
 * <td>{@link com.jamw.jmud.fields fields}</td><td></td><td>Package containing 
 * implementations of some practical {@linkplain Field field}s.</td>
 * </tr>
 * <tr>
 * <td>{@link Scales}</td><td></td><td>Abstract class of commonly used {@linkplain Scale scale}s 
 * of measurements.</td>
 * </tr>
 * <tr>
 * <td>{@link Units}</td><td></td><td>Abstract class of commonly used {@linkplain Unit unit}s and 
 * for creating new units.</td>
 * </tr>
 * <tr>
 * <td>{@link Universe}</td><td></td><td>Abstract class for creating new 
 * {@linkplain FundamentalDimension fundamental dimension}s and
 * {@linkplain FundamentalUnit fundamental unit}s via a 
 * {@linkplain FundamentalPair fundamental pair}.  Contains the special null dimension
 * {@linkplain Universe#getDimensionless "Dimensionless"}.</td>
 * </tr>
 * </table>
 * 
 * @author andreww1011
 */
package com.jamw.jmud;
