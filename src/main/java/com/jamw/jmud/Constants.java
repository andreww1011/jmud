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
 * Factory class of common universal constants and measures.
 * @author andreww1011
 */
public abstract class Constants<F extends Field<F>> {
    
    private Constants() {} 
    
    /**
     * The ratio of the circumference of a circle to its diameter, <i>\u03C0</i>, 
     * defined as 3.141 592 653 589 793 238 462 643 383 279.
     */
    public static final String pi =     "3.141592653589793238462643383279";
    
    /**
     * The base of natural logarithms, <i>e</i>, defined as 2.718 281 828 459 045 235 360 287 471 352.
     */
    public static final String euler =  "2.718281828459045235360287471352";
    
    /**
     * The speed of light in a vacuum, <i>c</i>, defined as 299 792 458 {@link Units#METER_PER_SECOND meters per second}.
     */
    public static final Expression c = Measures.take("299792458",Units.METER_PER_SECOND);
    
    /**
     * The Planck constant, <i>h</i>, defined as 
     * 6.626 070 15 × 10<sup>−34</sup> {@link Units#JOULE joule}⋅{@link Units#SECOND second}.
     */
    public static final Expression h = Measures.take("6.62607015E−34", Units.newUnit().as(Units.JOULE).multiply(Units.SECOND).create());

    /**
     * The reduced Planck constant, <i>\u0127</i>, defined as
     * {@link #h h} / 2⋅\u03C0.
     */
    public static final Expression h_bar = h.divide(2).divide(pi);
    
    /**
     * The gravitational constant, <i>G</i>, defined as
     * 6.674 30 × 10<sup>−11</sup> 
     * {@link Units#METER meter}<sup>3</sup>⋅{@link Units#KILOGRAM kilogram}<sup>-1</sup>⋅{@link Units#SECOND second}<sup>-2</sup>.
     */
    public static final Expression G = Measures.take("6.67430E−11", Units.newUnit().as(Units.METER,3).divide(Units.KILOGRAM).divide(Units.SECOND,2).create());
    
    /**
     * The vacuum electric permittivity, <i>\u03B5<sub>0</sub></i>, defined as
     * 8.854 187 812 8 × 10<sup>−12</sup> 
     * {@link Units#FARAD farad}/{@link Units#METER meter}.
     */
    public static final Expression eps_0 = Measures.take("8.8541878128E−12", Units.newUnit().as(Units.FARAD).divide(Units.METER).create());
    
    /**
     * The vacuum magnetic permeability, <i>\u03BC<sub>0</sub></i>, defined as
     * 1.256 637 062 12 × 10<sup>−6</sup> 
     * {@link Units#HENRY henry}/{@link Units#METER meter}.
     */
    public static final Expression mu_0 = Measures.take("1.25663706212E−6", Units.newUnit().as(Units.HENRY).divide(Units.METER).create());
            
    /**
     * The impedance of free space, <i>Z<sub>0</sub></i>, defined as
     * 376.730 313 668 
     * {@link Units#OHM ohm}.
     */
    public static final Expression Z_0 = Measures.take("376.730313668",Units.OHM);
    
    /**
     * The elementary charge, <i>e</i>, defined as
     * 1.602 176 634 × 10<sup>−19</sup>
     * {@link Units#COULOMB coulomb}.
     */
    public static final Expression e = Measures.take("1.602176634E−19",Units.COULOMB);
    
    /**
     * The hyperfine transition frequency of Cesium-133, <i>\u0394\u03BD<sub>Cs</sub></i>, defined as
     * 9 192 631 770
     * {@link Units#HERTZ hertz}.
     */
    public static final Expression Dnu_Cs = Measures.take("9192631770",Units.HERTZ);
    
    /**
     * The Avogadro constant, <i>N<sub>A</sub></i>, defined as
     * 6.022 140 76 × 10<sup>23</sup>
     * {@link Units#MOLE mole}<sup>-1</sup>.
     */
    public static final Expression N_A = Measures.take("6.02214076E23",Units.newUnit().as(Units.MOLE,-1).create());
    
    /**
     * The Boltzmann constant, <i>k<sub>B</sub></i>, defined as
     * 1.380 649 × 10<sup>−23</sup>
     * {@link Units#JOULE joule}/{@link Units#KELVIN kelvin}.
     */
    public static final Expression k_B = Measures.take("1.380649E−23",Units.newUnit().as(Units.JOULE).divide(Units.KELVIN).create());
    
    /**
     * The Josephson constant, <i>K<sub>J</sub></i>, defined as
     * 2⋅{@link #e e} / {@link #h h}.
     */
    public static final Expression K_J = Measures.take(2).multiply(e).divide(h);
    
    /**
     * The conductance quantum, <i>G<sub>0</sub></i>, defined as
     * 2⋅{@link #e e}<sup>2</sup> / {@link #h h}.
     */
    public static final Expression G_0 = K_J.multiply(e);
    
    /**
     * The von Klitzing constant, <i>R<sub>K</sub></i>, defined as
     * {@link #h h} / {@link #e e}<sup>2</sup>.
     */
    public static final Expression R_K = Measures.take(2).divide(G_0);
    
    /**
     * The mass of an electron, <i>m<sub>e</sub></i>, defined as
     * 9.109 383 701 5 × 10<sup>−31</sup> {@link Units#KILOGRAM kilogram}.
     */
    public static final Expression m_e = Measures.take("9.1093837015E−31",Units.KILOGRAM);
    
    /**
     * The mass of a proton, <i>m<sub>p</sub></i>, defined as
     * 1.672 621 923 69 × 10<sup>−27</sup> {@link Units#KILOGRAM kilogram}.
     */
    public static final Expression m_p = Measures.take("1.67262192369E−27",Units.KILOGRAM);
    
    /**
     * The unified atomic mass constant, <i>m<sub>u</sub></i>, defined as
     * 1.660 539 066 60 × 10<sup>−27</sup> {@link Units#KILOGRAM kilogram}.
     */
    public static final Expression m_u = Measures.take("1.66053906660E−27",Units.KILOGRAM);
    
    /**
     * The mass of a neutron, <i>m<sub>n</sub></i>, defined as
     * 1.674 927 498 04 × 10<sup>−27</sup> {@link Units#KILOGRAM kilogram}.
     */
    public static final Expression m_n = Measures.take("1.67492749804E−27",Units.KILOGRAM);
    
    private static final Expression eh_barover2 = e.multiply(h_bar).divide(2);
    
    /**
     * The Bohr magneton, <i>\u03BC<sub>B</sub></i>, defined as
     * {@link #e e}⋅{@link #h_bar \u0127} / 2⋅{@link #m_e m<sub>e</sub>}.
     */
    public static final Expression mu_B = eh_barover2.divide(m_e);
    
    /**
     * The nuclear magneton, <i>\u03BC<sub>N</sub></i>, defined as
     * {@link #e e}⋅{@link #h_bar \u0127} / 2⋅{@link #m_p m<sub>p</sub>}.
     */
    public static final Expression mu_N = eh_barover2.divide(m_p);
    
    /**
     * The Sommersfeld constant, <i>\u03B1</i>, defined as
     * 7.297 352 569 3 × 10<sup>−3</sup> {@link Units#UNITLESS -}.
     */
    public static final Expression alpha = Measures.take("7.2973525693E−3",Units.UNITLESS);
    
    /**
     * The Bohr radius, <i>a<sub>0</sub></i>, defined as
     * 5.291 772 109 03 × 10<sup>−11</sup> {@link Units#METER meter}.
     */
    public static final Expression a_0 = Measures.take("5.29177210903E−11",Units.METER);
    
    /**
     * The classical electron radius, <i>r<sub>e</sub></i>, defined as
     * 2.817 940 326 2 × 10<sup>−15</sup> {@link Units#METER meter}.
     */
    public static final Expression r_e = Measures.take("2.8179403262E−15",Units.METER);
    
    /**
     * The electron g-factor, <i>g<sub>e</sub></i>, defined as
     * −2.002 319 304 362 56 {@link Units#UNITLESS -}.
     */
    public static final Expression g_e = Measures.take("−2.00231930436256",Units.UNITLESS);
    
    /**
     * The Hartree energy, <i>E<sub>h</sub></i>, defined as
     * 4.359 744 722 207 1 × 10<sup>−18</sup> {@link Units#JOULE joule}.
     */
    public static final Expression E_h = Measures.take("4.3597447222071E−18",Units.JOULE);
    
    /**
     * The Rydberg constant, <i>R<sub>\u221E</sub></i>, defined as
     * 10 973 731.568 160 {@link Units#METER meter}<sup>−1</sup>.
     */
    public static final Expression R_inf = Measures.take("10973731.568160",Units.newUnit().as(Units.METER,-1).create());
    
    /**
     * The Thomson cross sections, <i>\u03C3<sub>e</sub></i>, defined as
     * 6.652 458 732 1 × 10<sup>−29</sup> {@link Units#SQUARE_METER meter<sup>2</sup>}.
     */
    public static final Expression sigma_e = Measures.take("6.6524587321E−29",Units.SQUARE_METER);
    
    /**
     * The Faraday constant, <i>F</i>, defined as
     * {@link #N_A N<sub>A</sub>}⋅{@link #e e}.
     */
    public static final Expression F = N_A.multiply(e);
    
    /**
     * The molar gas constant, <i>R</i>, defined as
     * {@link #N_A N<sub>A</sub>}⋅{@link #k_B k<sub>B</sub>}.
     */
    public static final Expression R = N_A.multiply(k_B);
    
    /**
     * The molar mass constant, <i>M<sub>u</sub></i>, defined as
     * 0.999 999 999 65 × 10<sup>−3</sup> {@link Units#KILOGRAM kilogram}/{@link Units#MOLE mole}.
     */
    public static final Expression M_u = Measures.take("0.99999999965E−3",Units.newUnit().as(Units.KILOGRAM).divide(Units.MOLE).create());
    
    /**
     * The Stefan-Boltzmann constant, <i>\u03C3</i>, defined as
     * {@link #pi \u03C0}<sup>2</sup>⋅{@link #k_B k<sub>B</sub>}<sup>4</sup> /
     * 60⋅{@link #h_bar \u0127}<sup>3</sup>⋅{@link #c c}<sup>2</sup>.
     */
    public static final Expression sigma = Measures
            .take(pi).multiply(pi)
            .multiply(k_B).multiply(k_B).multiply(k_B).multiply(k_B)
            .divide(Measures.take(60)
                      .multiply(h_bar).multiply(h_bar).multiply(h_bar)
                      .multiply(c).multiply(c));
}
