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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 *
 * @author andreww1011
 */
public abstract class Units {
    
    private Units(){}
    
    private static final String YOTTA   = "1000000000000000000000000";
    private static final String ZETTA   = "1000000000000000000000";
    private static final String EXA     = "1000000000000000000";
    private static final String PETA    = "1000000000000000";
    private static final String TERA    = "1000000000000";
    private static final String GIGA    = "1000000000";
    private static final String MEGA    = "1000000";
    private static final String KILO    = "1000";
    private static final String HECTO   = "100";
    private static final String DECA    = "10";
    private static final String DECI    = "0.1";
    private static final String CENTI   = "0.01";
    private static final String MILLI   = "0.001";
    private static final String MICRO   = "0.000001";
    private static final String NANO    = "0.000000001";
    private static final String PICO    = "0.000000000001";
    private static final String FEMTO   = "0.000000000000001";
    private static final String ATTO    = "0.000000000000000001";
    private static final String ZEPTO   = "0.000000000000000000001";
    private static final String YOCTO   = "0.000000000000000000000001";
    
    static final String PI = "3.141592653589793238462643383279";
    static final String E = "2.718281828459045235360287471352";
    
    /**
     * Fundamental unit for measurements of {@link Dimensions#DIMENSIONLESS no dimension}.
     * Denoted as <i>"-"</i>.
     */
    public static final FundamentalUnit UNITLESS = Universe.UNITLESS;
    /**
     * Fundamental unit for measurements of {@link Dimensions#LENGTH length}, defined as the distance
     * traveled by light in a vacuum in 1/299 792 458 {@link #SECOND second}.
     * Denoted as <i>"m"</i>.
     */
    public static final FundamentalUnit METER = Universe.METER;
    /**
     * Fundamental unit for measurements of {@link Dimensions#MASS mass}, defined as the mass of a reference
     * platinum-iridium alloy kept in France.
     * Denoted as <i>"kg"</i>.
     */
    public static final FundamentalUnit KILOGRAM = Universe.KILOGRAM;
    /**
     * Fundamental unit for measurements of {@link Dimensions#TIME time}, defined as the duration 
     * of 9 192 631 770 periods of the radiation corresponding to the 
     * transition between the two hyperfine levels of the ground state 
     * of the cesium-133 atom.
     * Denoted as <i>"s"</i>.
     */
    public static final FundamentalUnit SECOND = Universe.SECOND;
    /**
     * Fundamental unit for measurements of {@link Dimensions#ELECTRIC_CURRENT electric current}, defined as the 
     * constant current which produces a force of 2 * 10<sup>-7</sup> {@link #NEWTON newton} per {@link #METER meter}
     * between two straight parallel conductors of infinite length and 
     * negligible cross-sectional area, placed 1 {@link #METER meter} apart in a vacuum.
     * Denoted as <i>"A"</i>.
     */
    public static final FundamentalUnit AMPERE = Universe.AMPERE;
    /**
     * Fundamental unit for measurements of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature}, defined
     * such that the {@link Constants#k_B Boltzmann constant} takes the value
     * 1.380 649 × 10<sup>−23</sup> {@link #JOULE joule}/{@link #KELVIN kelvin} exactly.  
     * Denoted as <i>"K"</i>.
     */
    public static final FundamentalUnit KELVIN = Universe.KELVIN;
    /**
     * Fundamental unit for measurements of the {@link Dimensions#AMOUNT_OF_SUBSTANCE amount of substance} in a body 
     * or system, defined as the number of atoms in 0.012 {@link #KILOGRAM kilogram} 
     * of the carbon-12 element.
     * Denoted as <i>"mol"</i>.
     */
    public static final FundamentalUnit MOLE = Universe.MOLE;
    /**
     * Fundamental unit for measurements of {@link Dimensions#LUMINOUS_INTENSITY luminous intensity}, defined as 
     * the radiant intensity of 1/683 {@link #WATT watt} per {@link #STERADIAN steradian} from a source
     * that emits monochromatic radiation of frequency 5.4 * 10<sup>14</sup> {@link #HERTZ hertz}.
     * Denoted as <i>"cd"</i>.
     */
    public static final FundamentalUnit CANDELA = Universe.CANDELA;
    
    //SI and Common Units
    //DIMENSIONLESS
    /**
     * Unit of {@link Dimensions#ANGLE angle}.
     * <p>
     * Denoted as <i>"rad"</i>.
     */
    public static final Unit RADIAN;
    /**
     * Unit of {@link Dimensions#ANGLE angle}.
     * <p>
     * 1 degree = \u03C0/180 {@link #RADIAN radian}
     * <p>
     * Denoted as <i>"\u00B0"</i>.
     */
    public static final Unit DEGREE;
    /**
     * Unit of {@link Dimensions#SOLID_ANGLE solid angle}.
     * <p>
     * Denoted as <i>"sr"</i>.
     */
    public static final Unit STERADIAN;
    /**
     * Unit of {@link Dimensions#STRAIN strain} (change in length per length).
     */
    public static final Unit STRAIN;
    /**
     * Unit of one part per hundred.
     * <p>
     * Denoted as <i>"%"</i>.
     */
    public static final Unit PERCENT;
    
    //TIME
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 hertz = (1 {@link #SECOND second})<sup>-1</sup>
     * <p>
     * Denoted as <i>"Hz"</i>.
     */
    public static final Unit HERTZ;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 day = 86 400 {@link #SECOND second}
     * <p>
     * Denoted as <i>"d"</i>.
     */
    public static final Unit DAY;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 hour = 3 600 {@link #SECOND second}
     * <p>
     * Denoted as <i>"hr"</i>.
     */
    public static final Unit HOUR;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 minute = 60 {@link #SECOND second}
     * <p>
     * Denoted as <i>"min"</i>.
     */
    public static final Unit MINUTE;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 millisecond = 1 * 10<sup>-3</sup> {@link #SECOND second}
     * <p>
     * Denoted as <i>"ms"</i>.
     */
    public static final Unit MILLISECOND;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 microsecond = 1* 10<sup>-6</sup> {@link #SECOND second}
     * <p>
     * Denoted as <i>"\u03BCs"</i>.
     */
    public static final Unit MICROSECOND;
    /**
     * Unit of {@link Dimensions#TIME time}.
     * <p>
     * 1 nanosecond = 1 * 10<sup>-9</sup> {@link #SECOND second}
     * <p>
     * Denoted as <i>"ns"</i>.
     */
    public static final Unit NANOSECOND;
    
    //MASS
    /**
     * Unit of {@link Dimensions#MASS mass}.
     * <p>
     * 1 gram = 1 * 10<sup>-3</sup> {@link #KILOGRAM kilogram}
     * <p>
     * Denoted as <i>"g"</i>.
     */
    public static final Unit GRAM;
    /**
     * Unit of {@link Dimensions#MASS mass}.
     * <p>
     * 1 milligram = 1 * 10<sup>-3</sup> {@link #GRAM gram}
     * <p>
     * Denoted as <i>"mg"</i>.
     */
    public static final Unit MILLIGRAM;
    
    //LENGTH
    /**
     * Unit of {@link Dimensions#LENGTH length}.
     * <p>
     * 1 centimeter = 1 * 10<sup>-2</sup> {@link #METER meter}
     * <p>
     * Denoted as <i>"cm"</i>.
     */
    public static final Unit CENTIMETER;
    /**
     * Unit of {@link Dimensions#LENGTH length}.
     * <p>
     * 1 millimeter = 1 * 10<sup>-3</sup> {@link #METER meter}
     * <p>
     * Denoted as <i>"mm"</i>.
     */
    public static final Unit MILLIMETER;
    
    //AREA
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     * 1 square meter = (1 {@link #METER meter})<sup>2</sup>
     */
    public static final Unit SQUARE_METER;
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     * 1 square centimeter = (1 {@link #CENTIMETER centimeter})<sup>2</sup>
     */
    public static final Unit SQUARE_CENTIMETER;
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     *1 square millimeter = (1 {@link #MILLIMETER millimeter})<sup>2</sup>
     */
    public static final Unit SQUARE_MILLIMETER;
    
    //VOLUME
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic meter = (1 {@link #METER meter})<sup>3</sup>
     */
    public static final Unit CUBIC_METER;
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic centimeter = (1 {@link #CENTIMETER centimeter})<sup>3</sup>
     */
    public static final Unit CUBIC_CENTIMETER;
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic millimeter = (1 {@link #MILLIMETER millimeter})<sup>3</sup>
     */
    public static final Unit CUBIC_MILLIMETER;
    
    //VELOCITY
    /**
     * Unit of {@link Dimensions#VELOCITY velocity}.
     * <p>
     * 1 meter per second = 1 {@link #METER meter} / 1 {@link #SECOND second}
     */
    public static final Unit METER_PER_SECOND;
    /**
     * Unit of {@link Dimensions#VELOCITY velocity}.
     * <p>
     * 1 centimeter per second = 1 {@link #CENTIMETER centimeter} / 1 {@link #SECOND second}
     */
    public static final Unit CENTIMETER_PER_SECOND;
    
    //ACCELERATION
    /**
     * Unit of {@link Dimensions#ACCELERATION acceleration}.
     * <p>
     * 1 meter per square second = 1 {@link #METER meter} / (1 {@link #SECOND second})<sup>2</sup>
     */
    public static final Unit METER_PER_SQUARE_SECOND;
    /**
     * Unit of {@link Dimensions#ACCELERATION acceleration}.
     * <p>
     * 1 centimeter per square second = 1 {@link #CENTIMETER centimeter} / (1 {@link #SECOND second})<sup>2</sup>
     */
    public static final Unit CENTIMETER_PER_SQUARE_SECOND;
    
    //LINEAR_MASS_DENSITY
    /**
     * Unit of {@link Dimensions#LINEAR_MASS_DENSITY linear mass density}.
     * <p>
     * 1 kilogram per meter = 1 {@link #KILOGRAM kilogram} / 1 {@link #METER meter}
     */
    public static final Unit KILOGRAM_PER_METER;
    /**
     * Unit of {@link Dimensions#LINEAR_MASS_DENSITY linear mass density}.
     * <p>
     * 1 kilogram per centimeter = 1 {@link #KILOGRAM kilogram} / 1 {@link #CENTIMETER centimeter}
     */
    public static final Unit KILOGRAM_PER_CENTIMETER;
    
    //AREA_MASS_DENSITY
    /**
     * Unit of {@link Dimensions#AREA_MASS_DENSITY area mass density}.
     * <p>
     * 1 kilogram per square meter = 1 {@link #KILOGRAM kilogram} / (1 {@link #METER meter})<sup>2</sup>
     */
    public static final Unit KILOGRAM_PER_SQUARE_METER;
    /**
     * Unit of {@link Dimensions#AREA_MASS_DENSITY area mass density}.
     * <p>
     * 1 kilogram per square centimeter = 1 {@link #KILOGRAM kilogram} / (1 {@link #CENTIMETER centimeter})<sup>2</sup>
     */
    public static final Unit KILOGRAM_PER_SQUARE_CENTIMETER;
    /**
     * Unit of {@link Dimensions#AREA_MASS_DENSITY area mass density}.
     * <p>
     * 1 kilogram per square millimeter = 1 {@link #KILOGRAM kilogram} / (1 {@link #MILLIMETER millimeter})<sup>2</sup>
     */
    public static final Unit KILOGRAM_PER_SQUARE_MILLIMETER;
    
    //MASS_DENSITY
    /**
     * Unit of {@link Dimensions#MASS_DENSITY mass density}.
     * <p>
     * 1 kilogram per cubic meter = 1 {@link #KILOGRAM kilogram} / (1 {@link #METER meter})<sup>3</sup>
     */
    public static final Unit KILOGRAM_PER_CUBIC_METER;
    /**
     * Unit of {@link Dimensions#MASS_DENSITY mass density}.
     * <p>
     * 1 kilogram per cubic centimeter = 1 {@link #KILOGRAM kilogram} / (1 {@link #CENTIMETER centimeter})<sup>3</sup>
     */
    public static final Unit KILOGRAM_PER_CUBIC_CENTIMETER;
    /**
     * Unit of {@link Dimensions#MASS_DENSITY mass density}.
     * <p>
     * 1 kilogram per cubic millimeter = 1 {@link #KILOGRAM kilogram} / (1 {@link #MILLIMETER millimeter})<sup>3</sup>
     */
    public static final Unit KILOGRAM_PER_CUBIC_MILLIMETER;
    
    //FORCE, WEIGHT
    /**
     * Unit of {@link Dimensions#FORCE force}.
     * <p>
     * 1 newton = 1 {@link #KILOGRAM kilogram} * 1 {@link #METER_PER_SQUARE_SECOND meter per square second}
     */
    public static final Unit NEWTON;
    /**
     * Unit of {@link Dimensions#FORCE force}.
     * <p>
     * 1 kilonewton = 1 * 10<sup>3</sup> {@link #NEWTON newton}
     */
    public static final Unit KILONEWTON;
    /**
     * Unit of {@link Dimensions#FORCE force}.
     * <p>
     * 1 meganewton = 1 * 10<sup>6</sup> {@link #NEWTON newton}
     */
    public static final Unit MEGANEWTON;
    
    //MOMENT
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 newton meter = 1 {@link #NEWTON newton} * 1 {@link #METER meter}
     */
    public static final Unit NEWTON_METER;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 kilonewton meter = 1 {@link #KILONEWTON kilonewton} * 1 {@link #METER meter}
     */
    public static final Unit KILONEWTON_METER;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 newton centimeter = 1 {@link #NEWTON newton} * 1 {@link #CENTIMETER centimeter}
     */
    public static final Unit NEWTON_CENTIMETER;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 newton millimeter = 1 {@link #NEWTON newton} * 1 {@link #MILLIMETER millimeter}
     */
    public static final Unit NEWTON_MILLIMETER;
    
    //LINEAR_WEIGHT_DENSITY
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 newton per meter = 1 {@link #NEWTON newton} / 1 {@link #METER meter}
     */
    public static final Unit NEWTON_PER_METER;
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 kilonewton per meter = 1 {@link #KILONEWTON kilonewton} / 1 {@link #METER meter}
     */
    public static final Unit KILONEWTON_PER_METER;
    
    //AREA_WEIGHT_DENSITY, PRESSURE, STRESS
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 pascal = 1 {@link #NEWTON newton} / 1 {@link #SQUARE_METER square meter}
     */
    public static final Unit PASCAL;
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 kilopascal = 1 * 10<sup>3</sup> {@link #PASCAL pascal}
     */
    public static final Unit KILOPASCAL;
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 megapascal = 1 * 10<sup>6</sup> {@link #PASCAL pascal}
     */
    public static final Unit MEGAPASCAL;
    /**
     * Unit of {@link Dimensions#AREA_WEIGHT_DENSITY area weight density}.
     * <p>
     * 1 newton per square centimeter = 1 {@link #NEWTON newton} / 1 {@link #SQUARE_CENTIMETER square centimeter}
     */
    public static final Unit NEWTON_PER_SQUARE_CENTIMETER;
    /**
     * Unit of {@link Dimensions#AREA_WEIGHT_DENSITY area weight density}.
     * <p>
     * 1 kilonewton per square centimeter = 1 {@link #KILONEWTON kilonewton} / 1 {@link #SQUARE_CENTIMETER square centimeter}
     */
    public static final Unit KILONEWTON_PER_SQUARE_CENTIMETER;
    /**
     * Unit of {@link Dimensions#AREA_WEIGHT_DENSITY area weight density}.
     * <p>
     * 1 newton per square millimeter = 1 {@link #NEWTON newton} / 1 {@link #SQUARE_MILLIMETER square millimeter}
     */
    public static final Unit NEWTON_PER_SQUARE_MILLIMETER;
    /**
     * Unit of {@link Dimensions#AREA_WEIGHT_DENSITY area weight density}.
     * <p>
     * 1 kilonewton per square millimeter = 1 {@link #KILONEWTON kilonewton} / 1 {@link #SQUARE_MILLIMETER square millimeter}
     */
    public static final Unit KILONEWTON_PER_SQUARE_MILLIMETER;
    
    //WEIGHT_DENSITY
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 newton per cubic meter = 1 {@link #NEWTON newton} / 1 {@link #CUBIC_METER cubic meter}
     */
    public static final Unit NEWTON_PER_CUBIC_METER;
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 newton per cubic centimeter = 1 {@link #NEWTON newton} / 1 {@link #CUBIC_CENTIMETER cubic centimeter}
     */
    public static final Unit NEWTON_PER_CUBIC_CENTIMETER;
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 newton per cubic millimeter = 1 {@link #NEWTON newton} / 1 {@link #CUBIC_MILLIMETER cubic millimeter}
     */
    public static final Unit NEWTON_PER_CUBIC_MILLIMETER;
    
    //ENERGY, WORK, HEAT
    /**
     * Unit of {@link Dimensions#ENERGY energy}.
     * <p>
     * 1 joule = 1 {@link #NEWTON newton} * 1 {@link #METER meter}
     */
    public static final Unit JOULE;
    /**
     * Unit of {@link Dimensions#ENERGY energy}.
     * <p>
     * 1 kilojoule = 1 * 10<sup>3</sup> {@link #JOULE joule}
     */
    public static final Unit KILOJOULE;
    /**
     * Unit of {@link Dimensions#ENERGY energy}.
     * <p>
     * 1 megajoule = 1 * 10<sup>6</sup> {@link #JOULE joule}
     */
    public static final Unit MEGAJOULE;
    
    //POWER
    /**
     * Unit of {@link Dimensions#POWER power}.
     * <p>
     * 1 watt = 1 {@link #JOULE joule} / 1 {@link #SECOND second}
     */
    public static final Unit WATT;
    /**
     * Unit of {@link Dimensions#POWER power}.
     * <p>
     * 1 kilowatt = 1 * 10<sup>3</sup> {@link #WATT watt}
     */
    public static final Unit KILOWATT;
    /**
     * Unit of {@link Dimensions#POWER power}.
     * <p>
     * 1 megawatt = 1 * 10<sup>6</sup> {@link #WATT watt}
     */
    public static final Unit MEGAWATT;
    
    //ELECTRIC_CHARGE
    /**
     * Unit of {@link Dimensions#ELECTRIC_CHARGE electric charge}.
     * <p>
     * 1 coulomb = 1 {@link #AMPERE ampere} * 1 {@link #SECOND second}
     */
    public static final Unit COULOMB;
    
    //ELECTRIC_POTENTIAL
    /**
     * Unit of {@link Dimensions#ELECTRIC_POTENTIAL electric potential}.
     * <p>
     * 1 volt = 1 {@link #WATT watt} / 1 {@link #AMPERE ampere}
     */
    public static final Unit VOLT;
    
    //ELECTRIC_CAPACITANCE
    /**
     * Unit of {@link Dimensions#ELECTRIC_CAPACITANCE electric capacitance}.
     * <p>
     * 1 farad = 1 {@link #COULOMB coulomb} / 1 {@link #VOLT volt}
     */
    public static final Unit FARAD;
    
    //ELECTRIC_RESISTANCE
    /**
     * Unit of {@link Dimensions#ELECTRIC_RESISTANCE electric resistance}.
     * <p>
     * 1 ohm = 1 {@link #VOLT volt} / 1 {@link #AMPERE ampere}
     */
    public static final Unit OHM;
    
    //ELECTRIC_CONDUCTANCE
    /**
     * Unit of {@link Dimensions#ELECTRIC_CONDUCTANCE electric conductance}.
     * <p>
     * 1 siemens = (1 {@link #OHM ohm})<sup>-1</sup>
     * <p>
     * Denoted as <i>"\u2127"</i>.
     */
    public static final Unit SIEMENS;
    
    //MAGNETIC_FLUX
    /**
     * Unit of {@link Dimensions#MAGNETIC_FLUX magnetic flux}.
     * <p>
     * 1 weber = 1 {@link #VOLT volt} * 1 {@link #SECOND second}
     */
    public static final Unit WEBER;
    
    //AREA_MAGNETIC_FLUX_DENSITY
    /**
     * Unit of {@link Dimensions#AREA_MAGNETIC_FLUX_DENSITY area magnetic flux density}.
     * <p>
     * 1 tesla = 1 {@link #WEBER weber} / 1 {@link #SQUARE_METER square meter}
     */
    public static final Unit TESLA;
    
    //INDUCTANCE
    /**
     * Unit of {@link Dimensions#INDUCTANCE inductance}.
     * <p>
     * 1 henry = 1 {@link #WEBER weber} / 1 {@link #AMPERE ampere}
     */
    public static final Unit HENRY;
    
    //LUMINOUS_FLUX
    /**
     * Unit of {@link Dimensions#LUMINOUS_FLUX luminous flux}.
     * <p>
     * 1 lumen = 1 {@link #CANDELA candela} * 1 {@link #STERADIAN steradian}
     */
    public static final Unit LUMEN;
    
    //ILLUMINANCE
    /**
     * Unit of {@link Dimensions#ILLUMINANCE illuminance}.
     * <p>
     * 1 lux = 1 {@link #LUMEN lumen} / 1 {@link #SQUARE_METER square meter}
     */
    public static final Unit LUX;
    
    //RADIOACTIVITY
    /**
     * Unit of {@link Dimensions#RADIOACTIVITY radioactivity}.
     * <p>
     * 1 becquerel = (1 {@link #SECOND second})<sup>-1</sup>
     */
    public static final Unit BECQUEREL;
    
    //ABSORBED_DOSE
    /**
     * Unit of {@link Dimensions#ABSORBED_DOSE absorbed dose}.
     * <p>
     * 1 gray = 1 {@link #JOULE joule} / 1 {@link #KILOGRAM kilogram}
     */
    public static final Unit GRAY;
    
    //CATALYTIC_ACTIVITY
    /**
     * Unit of {@link Dimensions#CATALYTIC_ACTIVITY catalytic activity}.
     * <p>
     * 1 katal = 1 {@link #MOLE mole} / 1 {@link #SECOND second}
     */
    public static final Unit KATAL;
    
    static {
        //DIMENSIONLESS
        RADIAN          = newUnit().ofDimension(Dimensions.ANGLE).as(UNITLESS).withName("RADIAN").withSymbol("rad").create();
        DEGREE          = newUnit().ofDimension(Dimensions.ANGLE).asTheRatio(PI).over(180).ofA(RADIAN).withName("DEGREE").withSymbol("\u00B0").create();
        STERADIAN       = newUnit().ofDimension(Dimensions.SOLID_ANGLE).as(UNITLESS).withName("STERADIAN").withSymbol("sr").create();
        STRAIN          = newUnit().ofDimension(Dimensions.STRAIN).as(UNITLESS).withName("STRAIN").create();
        PERCENT         = newUnit().ofDimension(Dimensions.DIMENSIONLESS).asTheRatio(1).over(100).ofA(UNITLESS).withName("PERCENT").withSymbol("%").create();
        
        //TIME
        HERTZ           = newUnit().ofDimension(Dimensions.FREQUENCY).as(SECOND,-1).withName("HERTZ").withSymbol("Hz").create();
        DAY             = newUnit().ofDimension(Dimensions.TIME).asExactly("86400").ofA(SECOND).withName("DAY").withSymbol("d").create();
        HOUR            = newUnit().ofDimension(Dimensions.TIME).asExactly("3600").ofA(SECOND).withName("HOUR").withSymbol("hr").create();
        MINUTE          = newUnit().ofDimension(Dimensions.TIME).asExactly("60").ofA(SECOND).withName("MINUTE").withSymbol("min").create();
        MILLISECOND     = Units.milli(SECOND);
        MICROSECOND     = Units.micro(SECOND);
        NANOSECOND      = Units.nano(SECOND);
        
        //MASS
        GRAM            = newUnit().ofDimension(Dimensions.MASS).asExactly(MILLI).ofA(KILOGRAM).withName("GRAM").withSymbol("g").create();
        MILLIGRAM       = Units.milli(GRAM);
        
        //LENGTH
        CENTIMETER      = Units.centi(METER);
        MILLIMETER      = Units.milli(METER);
        
        //AREA
        SQUARE_METER            = newUnit().ofDimension(Dimensions.AREA).as(METER, Exponents.SQUARED).create();
        SQUARE_CENTIMETER       = newUnit().ofDimension(Dimensions.AREA).as(CENTIMETER, Exponents.SQUARED).create();
        SQUARE_MILLIMETER       = newUnit().ofDimension(Dimensions.AREA).as(MILLIMETER, Exponents.SQUARED).create();
        
        //VOLUME
        CUBIC_METER             = newUnit().ofDimension(Dimensions.VOLUME).as(METER, Exponents.CUBED).create();
        CUBIC_CENTIMETER        = newUnit().ofDimension(Dimensions.VOLUME).as(CENTIMETER, Exponents.CUBED).create();
        CUBIC_MILLIMETER        = newUnit().ofDimension(Dimensions.VOLUME).as(MILLIMETER, Exponents.CUBED).create();
        
        //VELOCITY
        METER_PER_SECOND        = newUnit().ofDimension(Dimensions.VELOCITY).as(METER).divide(SECOND).create();
        CENTIMETER_PER_SECOND   = newUnit().ofDimension(Dimensions.VELOCITY).as(CENTIMETER).divide(SECOND).create();
        
        //ACCELERATION
        METER_PER_SQUARE_SECOND         = newUnit().ofDimension(Dimensions.ACCELERATION).as(METER).divide(SECOND,2).create();
        CENTIMETER_PER_SQUARE_SECOND    = newUnit().ofDimension(Dimensions.ACCELERATION).as(CENTIMETER).divide(SECOND,2).create();
        
        //LINEAR_MASS_DENSITY
        KILOGRAM_PER_METER              = newUnit().ofDimension(Dimensions.LINEAR_MASS_DENSITY).as(KILOGRAM).divide(METER).create();
        KILOGRAM_PER_CENTIMETER         = newUnit().ofDimension(Dimensions.LINEAR_MASS_DENSITY).as(KILOGRAM).divide(CENTIMETER).create();

        //AREA_MASS_DENSITY
        KILOGRAM_PER_SQUARE_METER       = newUnit().ofDimension(Dimensions.AREA_MASS_DENSITY).as(KILOGRAM).divide(SQUARE_METER).create();
        KILOGRAM_PER_SQUARE_CENTIMETER  = newUnit().ofDimension(Dimensions.AREA_MASS_DENSITY).as(KILOGRAM).divide(SQUARE_CENTIMETER).create();
        KILOGRAM_PER_SQUARE_MILLIMETER  = newUnit().ofDimension(Dimensions.AREA_MASS_DENSITY).as(KILOGRAM).divide(SQUARE_MILLIMETER).create();
        
        //MASS_DENSITY
        KILOGRAM_PER_CUBIC_METER        = newUnit().ofDimension(Dimensions.MASS_DENSITY).as(KILOGRAM).divide(CUBIC_METER).create();
        KILOGRAM_PER_CUBIC_CENTIMETER   = newUnit().ofDimension(Dimensions.MASS_DENSITY).as(KILOGRAM).divide(CUBIC_CENTIMETER).create();
        KILOGRAM_PER_CUBIC_MILLIMETER   = newUnit().ofDimension(Dimensions.MASS_DENSITY).as(KILOGRAM).divide(CUBIC_MILLIMETER).create();
        
        //FORCE, WEIGHT
        NEWTON                  = newUnit().ofDimension(Dimensions.FORCE).as(KILOGRAM).multiply(METER_PER_SQUARE_SECOND).withName("NEWTON").withSymbol("N").create();
        KILONEWTON              = Units.kilo(NEWTON);
        MEGANEWTON              = Units.mega(NEWTON);
        
        //MOMENT
        NEWTON_METER            = newUnit().ofDimension(Dimensions.MOMENT).as(NEWTON).multiply(METER).create();
        KILONEWTON_METER        = newUnit().ofDimension(Dimensions.MOMENT).as(KILONEWTON).multiply(METER).create();
        NEWTON_CENTIMETER       = newUnit().ofDimension(Dimensions.MOMENT).as(NEWTON).multiply(CENTIMETER).create();
        NEWTON_MILLIMETER       = newUnit().ofDimension(Dimensions.MOMENT).as(NEWTON).multiply(MILLIMETER).create();

        //LINEAR_WEIGHT_DENSITY
        NEWTON_PER_METER        = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(NEWTON).divide(METER).create();
        KILONEWTON_PER_METER    = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(KILONEWTON).divide(METER).create();
        
        //AREA_WEIGHT_DENSITY, PRESSURE, STRESS
        PASCAL                              = newUnit().ofDimension(Dimensions.PRESSURE).as(NEWTON).divide(SQUARE_METER).withName("PASCAL").withSymbol("Pa").create();
        KILOPASCAL                          = Units.kilo(PASCAL);
        MEGAPASCAL                          = Units.mega(PASCAL);
        NEWTON_PER_SQUARE_CENTIMETER        = newUnit().ofDimension(Dimensions.AREA_WEIGHT_DENSITY).as(NEWTON).divide(SQUARE_CENTIMETER).create();
        KILONEWTON_PER_SQUARE_CENTIMETER    = newUnit().ofDimension(Dimensions.AREA_WEIGHT_DENSITY).as(KILONEWTON).divide(SQUARE_CENTIMETER).create();
        NEWTON_PER_SQUARE_MILLIMETER        = newUnit().ofDimension(Dimensions.AREA_WEIGHT_DENSITY).as(NEWTON).divide(SQUARE_MILLIMETER).create();
        KILONEWTON_PER_SQUARE_MILLIMETER    = newUnit().ofDimension(Dimensions.AREA_WEIGHT_DENSITY).as(KILONEWTON).divide(SQUARE_MILLIMETER).create();
        
        //WEIGHT_DENSITY
        NEWTON_PER_CUBIC_METER              = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(NEWTON).divide(CUBIC_METER).create();
        NEWTON_PER_CUBIC_CENTIMETER         = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(NEWTON).divide(CUBIC_CENTIMETER).create();
        NEWTON_PER_CUBIC_MILLIMETER         = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(NEWTON).divide(CUBIC_MILLIMETER).create();
        
        //ENERGY, WORK, HEAT
        JOULE                   = newUnit().ofDimension(Dimensions.ENERGY).as(NEWTON).multiply(METER).withName("JOULE").withSymbol("J").create();
        KILOJOULE               = Units.kilo(JOULE);
        MEGAJOULE               = Units.mega(JOULE);

        //POWER
        WATT                    = newUnit().ofDimension(Dimensions.POWER).as(JOULE).divide(SECOND).withName("WATT").withSymbol("W").create();
        KILOWATT                = Units.kilo(WATT);
        MEGAWATT                = Units.mega(WATT);

        //ELECTRIC_CHARGE
        COULOMB                 = newUnit().ofDimension(Dimensions.ELECTRIC_CHARGE).as(AMPERE).multiply(SECOND).withName("COULOMB").withSymbol("C").create();
 
        //ELECTRIC_POTENTIAL
        VOLT                    = newUnit().ofDimension(Dimensions.ELECTRIC_POTENTIAL).as(WATT).divide(AMPERE).withName("VOLT").withSymbol("V").create();
        
        //ELECTRIC_CAPACITANCE
        FARAD                   = newUnit().ofDimension(Dimensions.ELECTRIC_CAPACITANCE).as(COULOMB).divide(VOLT).withName("FARAD").withSymbol("F").create();
        
        //ELECTRIC_RESISTANCE
        OHM                     = newUnit().ofDimension(Dimensions.ELECTRIC_RESISTANCE).as(VOLT).divide(AMPERE).withName("OHM").withSymbol("\u03A9").create();
        
        //ELECTRIC_CONDUCTANCE
        SIEMENS                 = newUnit().ofDimension(Dimensions.ELECTRIC_CONDUCTANCE).as(OHM,-1).withName("SIEMENS").withSymbol("\u2127").create();
        
        //MAGNETIC_FLUX
        WEBER                   = newUnit().ofDimension(Dimensions.MAGNETIC_FLUX).as(VOLT).multiply(SECOND).withName("WEBER").withSymbol("Wb").create();
        
        //AREA_MAGNETIC_FLUX_DENSITY
        TESLA                   = newUnit().ofDimension(Dimensions.AREA_MAGNETIC_FLUX_DENSITY).as(WEBER).divide(SQUARE_METER).withName("TESLA").withSymbol("T").create();
        
        //INDUCTANCE
        HENRY                   = newUnit().ofDimension(Dimensions.INDUCTANCE).as(WEBER).divide(AMPERE).withName("HENRY").withSymbol("H").create();
        
        //LUMINOUS_FLUX
        LUMEN                   = newUnit().ofDimension(Dimensions.LUMINOUS_FLUX).as(CANDELA).multiply(STERADIAN).withName("LUMEN").withSymbol("lm").create();
        
        //ILLUMINANCE
        LUX                     = newUnit().ofDimension(Dimensions.ILLUMINANCE).as(LUMEN).divide(SQUARE_METER).withName("LUX").withSymbol("lx").create();
        
        //RADIOACTIVITY
        BECQUEREL               = newUnit().ofDimension(Dimensions.RADIOACTIVITY).as(SECOND,-1).withName("BECQUEREL").withSymbol("Bq").create();
        
        //ABSORBED_DOSE
        GRAY                    = newUnit().ofDimension(Dimensions.ABSORBED_DOSE).as(JOULE).divide(KILOGRAM).withName("GRAY").withSymbol("Gy").create();
        
        //CATALYTIC_ACTIVITY
        KATAL                   = newUnit().ofDimension(Dimensions.CATALYTIC_ACTIVITY).as(MOLE).divide(SECOND).withName("KATAL").withSymbol("kat").create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>24</sup> times the specified unit.
     * Denoted by the prefix <i>"Y"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit yotta(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(YOTTA).ofA(u).withName("YOTTA" + u.getName()).withSymbol("Y" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>21</sup> times the specified unit.
     * Denoted by the prefix <i>"Z"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit zetta(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(ZETTA).ofA(u).withName("ZETTA" + u.getName()).withSymbol("Z" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>18</sup> times the specified unit.
     * Denoted by the prefix <i>"E"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit exa(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(EXA).ofA(u).withName("EXA" + u.getName()).withSymbol("E" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>15</sup> times the specified unit.
     * Denoted by the prefix <i>"P"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit peta(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(PETA).ofA(u).withName("PETA" + u.getName()).withSymbol("P" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>12</sup> times the specified unit.
     * Denoted by the prefix <i>"T"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit tera(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(TERA).ofA(u).withName("TERA" + u.getName()).withSymbol("T" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>9</sup> times the specified unit.
     * Denoted by the prefix <i>"G"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit giga(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(GIGA).ofA(u).withName("GIGA" + u.getName()).withSymbol("G" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>6</sup> times the specified unit.
     * Denoted by the prefix <i>"M"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit mega(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(MEGA).ofA(u).withName("MEGA" + u.getName()).withSymbol("M" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>3</sup> times the specified unit.
     * Denoted by the prefix <i>"k"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit kilo(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(KILO).ofA(u).withName("KILO" + u.getName()).withSymbol("k" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>2</sup> times the specified unit.
     * Denoted by the prefix <i>"h"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit hecto(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(HECTO).ofA(u).withName("HECTO" + u.getName()).withSymbol("h" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10 times the specified unit.
     * Denoted by the prefix <i>"da"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit deca(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(DECA).ofA(u).withName("DECA" + u.getName()).withSymbol("da" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-1</sup> times the specified unit.
     * Denoted by the prefix <i>"d"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit deci(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(DECI).ofA(u).withName("DECI" + u.getName()).withSymbol("d" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-2</sup> times the specified unit.
     * Denoted by the prefix <i>"c"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit centi(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(CENTI).ofA(u).withName("CENTI" + u.getName()).withSymbol("c" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-3</sup> times the specified unit.
     * Denoted by the prefix <i>"m"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit milli(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(MILLI).ofA(u).withName("MILLI" + u.getName()).withSymbol("m" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-6</sup> times the specified unit.
     * Denoted by the prefix <i>"\u03BC"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit micro(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(MICRO).ofA(u).withName("MICRO" + u.getName()).withSymbol("\u03BC" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-9</sup> times the specified unit.
     * Denoted by the prefix <i>"n"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit nano(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(NANO).ofA(u).withName("NANO" + u.getName()).withSymbol("n" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-12</sup> times the specified unit.
     * Denoted by the prefix <i>"p"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit pico(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(PICO).ofA(u).withName("PICO" + u.getName()).withSymbol("p" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-15</sup> times the specified unit.
     * Denoted by the prefix <i>"f"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit femto(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(FEMTO).ofA(u).withName("FEMTO" + u.getName()).withSymbol("f" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-18</sup> times the specified unit.
     * Denoted by the prefix <i>"a"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit atto(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(ATTO).ofA(u).withName("ATTO" + u.getName()).withSymbol("a" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-21</sup> times the specified unit.
     * Denoted by the prefix <i>"z"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit zepto(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(ZEPTO).ofA(u).withName("ZEPTO" + u.getName()).withSymbol("z" + u.getSymbol()).create();
    }
    
    /**
     * Returns a new unit that is a factor 10<sup>-24</sup> times the specified unit.
     * Denoted by the prefix <i>"y"</i>.
     * @param u unit
     * @return 
     */
    public static final Unit yocto(Unit u) {
        return newUnit().ofDimension(u.getDimension()).asExactly(YOCTO).ofA(u).withName("YOCTO" + u.getName()).withSymbol("y" + u.getSymbol()).create();
    }
    
    //USC Units
    //REFERENCE UNITS
    /**
     * Unit for measurements of {@link Dimensions#LENGTH length}, defined as 
     * 0.3048 {@link #METER meter} exactly.
     * Denoted as <i>"ft"</i>.
     */
    public static final Unit FOOT;
    /**
     * Unit for measurements of {@link Dimensions#MASS mass}, defined as the mass 
     * that is accelerated by 1 {@link #FOOT_PER_SQUARE_SECOND foot per square second} when
     * a force of 1 {@link #POUND pound} is exerted on it.
     * Denoted as <i>"slug"</i>.
     */
    public static final Unit SLUG;
    /**
     * Unit for measurements of {@link Dimensions#THERMODYNAMIC_TEMPERATURE thermodynamic temperature}, defined
     * as 5/9 {@link SystemeInternational#KELVIN kelvin}.  
     * Denoted as <i>"R"</i>.
     */
    public static final Unit RANKINE;
    
    //LENGTH
    /**
     * Unit of {@link Dimensions#LENGTH length}.
     * <p>
     * 1 inch = 1/12 {@link #FOOT foot}
     */
    public static final Unit INCH;
    /**
     * Unit of {@link Dimensions#LENGTH length}.
     * <p>
     * 1 yard = 3 {@link #FOOT foot}
     */
    public static final Unit YARD;
    
    //AREA
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     * 1 square foot = (1 {@link #FOOT foot})<sup>2</sup>
     */
    public static final Unit SQUARE_FOOT;
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     * 1 square inch = (1 {@link #INCH inch})<sup>2</sup>
     */
    public static final Unit SQUARE_INCH;
    /**
     * Unit of {@link Dimensions#AREA area}.
     * <p>
     * 1 square yard = (1 {@link #YARD yard})<sup>2</sup>
     */
    public static final Unit SQUARE_YARD;
    
    //VOLUME
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic foot = (1 {@link #FOOT foot})<sup>3</sup>
     */
    public static final Unit CUBIC_FOOT;
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic inch = (1 {@link #INCH inch})<sup>3</sup>
     */
    public static final Unit CUBIC_INCH;
    /**
     * Unit of {@link Dimensions#VOLUME volume}.
     * <p>
     * 1 cubic yard = (1 {@link #YARD yard})<sup>3</sup>
     */
    public static final Unit CUBIC_YARD;
    
    //VELOCITY
    /**
     * Unit of {@link Dimensions#VELOCITY velocity}.
     * <p>
     * 1 foot per second = 1 {@link #FOOT foot} / 1 {@link #SECOND second}
     */
    public static final Unit FOOT_PER_SECOND;
    /**
     * Unit of {@link Dimensions#VELOCITY velocity}.
     * <p>
     * 1 inch per second = 1 {@link #INCH inch} / 1 {@link #SECOND second}
     */
    public static final Unit INCH_PER_SECOND;
    
    //ACCELERATION
    /**
     * Unit of {@link Dimensions#ACCELERATION acceleration}.
     * <p>
     * 1 foot per square second = 1 {@link #FOOT foot} / (1 {@link #SECOND second})<sup>2</sup>
     */
    public static final Unit FOOT_PER_SQUARE_SECOND;
    /**
     * Unit of {@link Dimensions#ACCELERATION acceleration}.
     * <p>
     * 1 inch per square second = 1 {@link #INCH inch} / (1 {@link #SECOND second})<sup>2</sup>
     */
    public static final Unit INCH_PER_SQUARE_SECOND;
    
    //LINEAR_MASS_DENSITY
    /**
     * Unit of {@link Dimensions#LINEAR_MASS_DENSITY linear mass density}.
     * <p>
     * 1 slug per foot = 1 {@link #SLUG slug} / 1 {@link #FOOT foot}
     */
    public static final Unit SLUG_PER_FOOT;
    /**
     * Unit of {@link Dimensions#LINEAR_MASS_DENSITY linear mass density}.
     * <p>
     * 1 slug per inch = 1 {@link #SLUG slug} / 1 {@link #INCH inch}
     */
    public static final Unit SLUG_PER_INCH;
    
    //AREA_MASS_DENSITY
    /**
     * Unit of {@link Dimensions#AREA_MASS_DENSITY area mass density}.
     * <p>
     * 1 slug per square foot = 1 {@link #SLUG slug} / (1 {@link #FOOT foot})<sup>2</sup>
     */
    public static final Unit SLUG_PER_SQUARE_FOOT;
    /**
     * Unit of {@link Dimensions#AREA_MASS_DENSITY area mass density}.
     * <p>
     * 1 slug per square inch = 1 {@link #SLUG slug} / (1 {@link #INCH inch})<sup>2</sup>
     */
    public static final Unit SLUG_PER_SQUARE_INCH;
    
    //MASS_DENSITY
    /**
     * Unit of {@link Dimensions#MASS_DENSITY mass density}.
     * <p>
     * 1 slug per cubic foot = 1 {@link #SLUG slug} / (1 {@link #FOOT foot})<sup>3</sup>
     */
    public static final Unit SLUG_PER_CUBIC_FOOT;
    /**
     * Unit of {@link Dimensions#MASS_DENSITY mass density}.
     * <p>
     * 1 slug per cubic inch = 1 {@link #SLUG slug} / (1 {@link #INCH inch})<sup>3</sup>
     */
    public static final Unit SLUG_PER_CUBIC_INCH;
    
    //FORCE, WEIGHT
    /**
     * Unit of {@link Dimensions#FORCE force}.
     * <p>
     * 1 pound = 1 {@link #SLUG slug} * 1 {@link #FOOT_PER_SQUARE_SECOND foot per square second}
     */
    public static final Unit POUND;
    /**
     * Unit of {@link Dimensions#FORCE force}.
     * <p>
     * 1 kip = 1 000 {@link #POUND pound}
     */
    public static final Unit KIP;
    
    //MOMENT
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 pound foot = 1 {@link #POUND pound} * 1 {@link #FOOT foot}
     */
    public static final Unit POUND_FOOT;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 pound inch = 1 {@link #POUND pound} * 1 {@link #INCH inch}
     */
    public static final Unit POUND_INCH;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 kip foot = 1 {@link #KIP kip} * 1 {@link #FOOT foot}
     */
    public static final Unit KIP_FOOT;
    /**
     * Unit of {@link Dimensions#MOMENT moment}.
     * <p>
     * 1 kip inch = 1 {@link #KIP kip} * 1 {@link #INCH inch}
     */
    public static final Unit KIP_INCH;
    
    //LINEAR_WEIGHT_DENSITY
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 pound per foot = 1 {@link #POUND pound} / 1 {@link #FOOT foot}
     */
    public static final Unit POUND_PER_FOOT;
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 pound per inch = 1 {@link #POUND pound} / 1 {@link #INCH inch}
     */
    public static final Unit POUND_PER_INCH;
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 kip per foot = 1 {@link #KIP kip} / 1 {@link #FOOT foot}
     */
    public static final Unit KIP_PER_FOOT;
    /**
     * Unit of {@link Dimensions#LINEAR_WEIGHT_DENSITY linear weight density}.
     * <p>
     * 1 kip per inch = 1 {@link #KIP kip} / 1 {@link #INCH inch}
     */
    public static final Unit KIP_PER_INCH;
    
    //AREA_WEIGHT_DENSITY, PRESSURE, STRESS
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 pound per square foot = 1 {@link #POUND pound} / 1 {@link #SQUARE_FOOT square foot}
     */
    public static final Unit POUND_PER_SQUARE_FOOT;
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 pound per square inch = 1 {@link #POUND pound} / 1 {@link #SQUARE_INCH square inch}
     */
    public static final Unit POUND_PER_SQUARE_INCH;
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 kip per square foot = 1 {@link #KIP kip} / 1 {@link #SQUARE_FOOT square foot}
     */
    public static final Unit KIP_PER_SQUARE_FOOT;
    /**
     * Unit of {@link Dimensions#PRESSURE pressure}.
     * <p>
     * 1 kip per square inch = 1 {@link #KIP kip} / 1 {@link #SQUARE_INCH square inch}
     */
    public static final Unit KIP_PER_SQUARE_INCH;
    
    //WEIGHT_DENSITY
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 pound per cubic foot = 1 {@link #POUND pound} / 1 {@link #CUBIC_FOOT cubic foot}
     */
    public static final Unit POUND_PER_CUBIC_FOOT;
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 pound per cubic inch = 1 {@link #POUND pound} / 1 {@link #CUBIC_INCH cubic inch}
     */
    public static final Unit POUND_PER_CUBIC_INCH;
    /**
     * Unit of {@link Dimensions#WEIGHT_DENSITY weight density}.
     * <p>
     * 1 pound per cubic yard = 1 {@link #POUND pound} / 1 {@link #CUBIC_YARD cubic yard}
     */
    public static final Unit POUND_PER_CUBIC_YARD;
    
    static {
        //REFERENCE UNITS
        FOOT            = newUnit().ofDimension(Dimensions.LENGTH).asExactly("0.3048").ofA(METER).withName("FOOT").withSymbol("ft").create();
        SLUG            = newUnit().ofDimension(Dimensions.MASS).asExactly("14.593903").ofA(KILOGRAM).withName("SLUG").withSymbol("slug").create();
        RANKINE         = newUnit().ofDimension(Dimensions.THERMODYNAMIC_TEMPERATURE).asTheRatio(5).over(9).ofA(KELVIN).withName("RANKINE").withSymbol("R").create();
        
        //LENGTH
        INCH            = newUnit().ofDimension(Dimensions.LENGTH).asTheRatio(1).over(12).ofA(FOOT).withName("INCH").withSymbol("in").create();
        YARD            = newUnit().ofDimension(Dimensions.LENGTH).asExactly(3).ofA(FOOT).withName("YARD").withSymbol("yd").create();
        
        //AREA
        SQUARE_FOOT     = newUnit().ofDimension(Dimensions.AREA).as(FOOT, Exponents.SQUARED).create();
        SQUARE_INCH     = newUnit().ofDimension(Dimensions.AREA).as(INCH, Exponents.SQUARED).create();
        SQUARE_YARD     = newUnit().ofDimension(Dimensions.AREA).as(YARD, Exponents.SQUARED).create();
        
        //VOLUME
        CUBIC_FOOT      = newUnit().ofDimension(Dimensions.VOLUME).as(FOOT, Exponents.CUBED).create();
        CUBIC_INCH      = newUnit().ofDimension(Dimensions.VOLUME).as(INCH, Exponents.CUBED).create();
        CUBIC_YARD      = newUnit().ofDimension(Dimensions.VOLUME).as(YARD, Exponents.CUBED).create();
        
        //VELOCITY
        FOOT_PER_SECOND = newUnit().ofDimension(Dimensions.VELOCITY).as(FOOT).divide(SECOND).create();
        INCH_PER_SECOND = newUnit().ofDimension(Dimensions.VELOCITY).as(INCH).divide(SECOND).create();
        
        //ACCELERATION
        FOOT_PER_SQUARE_SECOND  = newUnit().ofDimension(Dimensions.ACCELERATION).as(FOOT).divide(SECOND,Exponents.SQUARED).create();
        INCH_PER_SQUARE_SECOND  = newUnit().ofDimension(Dimensions.ACCELERATION).as(INCH).divide(SECOND,Exponents.SQUARED).create();
        
        //LINEAR_MASS_DENSITY
        SLUG_PER_FOOT   = newUnit().ofDimension(Dimensions.LINEAR_MASS_DENSITY).as(SLUG).divide(FOOT).create();
        SLUG_PER_INCH   = newUnit().ofDimension(Dimensions.LINEAR_MASS_DENSITY).as(SLUG).divide(INCH).create();
        
        //AREA_MASS_DENSITY
        SLUG_PER_SQUARE_FOOT    = newUnit().ofDimension(Dimensions.AREA_MASS_DENSITY).as(SLUG).divide(SQUARE_FOOT).create();
        SLUG_PER_SQUARE_INCH    = newUnit().ofDimension(Dimensions.AREA_MASS_DENSITY).as(SLUG).divide(SQUARE_INCH).create();
        
        //MASS_DENSITY
        SLUG_PER_CUBIC_FOOT     = newUnit().ofDimension(Dimensions.MASS_DENSITY).as(SLUG).divide(CUBIC_FOOT).create();
        SLUG_PER_CUBIC_INCH     = newUnit().ofDimension(Dimensions.MASS_DENSITY).as(SLUG).divide(CUBIC_INCH).create();
        
        //FORCE, WEIGHT
        POUND           = newUnit().ofDimension(Dimensions.FORCE).as(SLUG).multiply(FOOT_PER_SQUARE_SECOND).create();
        KIP             = newUnit().ofDimension(Dimensions.FORCE).asExactly(1000).ofA(POUND).withName("KIP").withSymbol("k").create();
        
        //MOMENT
        POUND_FOOT      = newUnit().ofDimension(Dimensions.MOMENT).as(POUND).multiply(FOOT).create();
        POUND_INCH      = newUnit().ofDimension(Dimensions.MOMENT).as(POUND).multiply(INCH).create();
        KIP_FOOT        = newUnit().ofDimension(Dimensions.MOMENT).as(KIP).multiply(FOOT).create();
        KIP_INCH        = newUnit().ofDimension(Dimensions.MOMENT).as(KIP).multiply(INCH).create();
        
        //LINEAR_WEIGHT_DENSITY
        POUND_PER_FOOT  = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(POUND).divide(FOOT).create();
        POUND_PER_INCH  = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(POUND).divide(INCH).create();
        KIP_PER_FOOT    = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(KIP).divide(FOOT).create();
        KIP_PER_INCH    = newUnit().ofDimension(Dimensions.LINEAR_WEIGHT_DENSITY).as(KIP).divide(INCH).create();
        
        //AREA_WEIGHT_DENSITY, PRESSURE, STRESS
        POUND_PER_SQUARE_FOOT   = newUnit().ofDimension(Dimensions.PRESSURE).as(POUND).divide(SQUARE_FOOT).create();
        POUND_PER_SQUARE_INCH   = newUnit().ofDimension(Dimensions.PRESSURE).as(POUND).divide(SQUARE_INCH).withSymbol("psi").create();
        KIP_PER_SQUARE_FOOT     = newUnit().ofDimension(Dimensions.PRESSURE).as(KIP).divide(SQUARE_FOOT).create();
        KIP_PER_SQUARE_INCH     = newUnit().ofDimension(Dimensions.PRESSURE).as(KIP).divide(SQUARE_INCH).withSymbol("ksi").create();
        
        //WEIGHT_DENSITY
        POUND_PER_CUBIC_FOOT    = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(POUND).divide(CUBIC_FOOT).create();
        POUND_PER_CUBIC_INCH    = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(POUND).divide(CUBIC_INCH).create();
        POUND_PER_CUBIC_YARD    = newUnit().ofDimension(Dimensions.WEIGHT_DENSITY).as(POUND).divide(CUBIC_YARD).create();
    }
    
    static abstract class AbstractUnit implements Unit {
            
        private final String name,symbol;

        AbstractUnit(String name, String symbol) {
            this.name = name;
            this.symbol = symbol;
        }

        @Override
        public final String getName() {
            return name;
        }

        @Override
        public final String getSymbol() {
            return symbol;
        }

        @Override
        public final boolean equals(Object o) {
            return super.equals(o);
        }

        @Override
        public final int hashCode() {
            return super.hashCode();
        }
    }
    
    public static final UnitBuilder newUnit() {
        return new UnitBuilderImpl(UnitBuilderHelper.NULL_HELPER);
    }
        
    private static final class UnitBuilderHelper {
        
        private static final UnitBuilderHelper NULL_HELPER 
        = new UnitBuilderHelper(null,new HashMap<>(),null,null,null,null);
        
        private final Dimension dimension;
        private final Scalar scale;
        private final Unit refUnit;
        private final String name,symbol;
        private final Map<Unit,Exponent> compoundMap;
        
        private UnitBuilderHelper(Dimension d,
                                  Map<Unit,Exponent> compoundMap,
                                  Unit referenceUnit,
                                  Scalar scale,
                                  String name,
                                  String symbol) {
            this.dimension = d;
            this.scale = scale;
            this.refUnit = referenceUnit;
            this.name = name;
            this.symbol = symbol;
            this.compoundMap = compoundMap;
        }
        
        private UnitBuilderHelper duplicate() {
            return new UnitBuilderHelper(
                    dimension,
                    new HashMap<>(compoundMap),
                    refUnit,
                    scale,
                    name,
                    symbol);
        }
        
        private UnitBuilderHelper put(Unit u, Exponent e) {
            Map<Unit,Exponent> map = putEntry(compoundMap,u,e);
            return new UnitBuilderHelper(
                    dimension,
                    map,
                    refUnit,
                    scale,
                    name,
                    symbol);
        }
        
        private static <T extends Unit> Map<T,Exponent> putEntry(Map<T,Exponent> map, T u, Exponent e) {
            Map<T,Exponent> m = new HashMap<>(map);
            if (m.containsKey(u)) {
                m.put(u, Exponents.product(m.get(u), e));
            } else {
                m.put(u, e);
            }
            return m;
        }
        
        private UnitBuilderHelper setDimension(Dimension d) {
            return new UnitBuilderHelper(
                    d,
                    new HashMap<>(compoundMap),
                    refUnit,
                    scale,
                    name,
                    symbol);
        }
        
        private UnitBuilderHelper setName(String name) {
            return new UnitBuilderHelper(
                    dimension,
                    new HashMap<>(compoundMap),
                    refUnit,
                    scale,
                    name,
                    symbol);
        }
        
        private UnitBuilderHelper setSymbol(String symbol) {
            return new UnitBuilderHelper(
                    dimension,
                    new HashMap<>(compoundMap),
                    refUnit,
                    scale,
                    name,
                    symbol);
        }
        
        private UnitBuilderHelper setScale(Scalar scalar) {
            return new UnitBuilderHelper(
                    dimension,
                    new HashMap<>(compoundMap),
                    refUnit,
                    scalar,
                    name,
                    symbol);
        }
        
        private UnitBuilderHelper setUnit(Unit u) {
            return new UnitBuilderHelper(
                    dimension,
                    new HashMap<>(compoundMap),
                    u,
                    scale,
                    name,
                    symbol);
        }
        
        private Unit create() {
            if (!compoundMap.isEmpty()) {
                if (scale != null)
                    throw new IllegalStateException("scale not unity.");
                else 
                    return createCompoundUnit();
            } else {
                return createRatioUnit();
            }
        }
        
        
        private Unit createCompoundUnit() {
            Map<Unit,Exponent> map = new HashMap<>(compoundMap);
            cleanCompoundMap(map);
            Scalar n = Expressions.ONE; //magic number
            Scalar d = Expressions.ONE; //magic number
            DimensionBuilder db = Dimensions.newDimension();
            for (Entry<Unit,Exponent> en : map.entrySet()) {
                Unit u = en.getKey();
                Exponent e = en.getValue();
                db = db.append(u.getDimension(),e);
                Exponent ex;
                if (e.isGreaterThan(Exponents.ZERO)) {
                    ex = e;
                } else {
                    ex = Exponents.negate(e);
                }
                Scalar c;
                if (ex.isEqualTo(Exponents.ONE)) {
                    c = u.getScale();
                } else {
                    c = u.getScale().power(ex);
                }
                if (e.isGreaterThan(Exponents.ZERO)) {
                    n = n.multiply(c);
                } else {
                    d = d.multiply(c);
                }
            }
            Scalar scal = n.divide(d);
            Dimension dim = db.create();
            if (dimension != null) 
                checkDimension(dim,dimension);
            String nn = isBlank(name) ? calcCompoundName(map) : name;
            String ss = isBlank(symbol) ? calcCompoundSymbol(map) : symbol;
            return new UnitImpl(dim,scal,nn,ss);
        }
        
        private static void cleanCompoundMap(Map<Unit,Exponent> map) {
            removeZeroExponents(map);
            if (map.isEmpty())
                map.put(Units.UNITLESS, Exponents.ONE);
        }
        
        private static void removeZeroExponents(Map<Unit,Exponent> map) {
            map.entrySet().stream()
                    .filter((e) -> !e.getKey().equals(Units.UNITLESS) && !e.getValue().isEqualTo(Exponents.ZERO))
                    .collect(Collectors.toMap((e) -> e.getKey(),(e) -> e.getValue()));
        }
        
        private static boolean isBlank(String s) {
            return s == null || s.isBlank();
        }
        
        private static void checkDimension(Dimension thisUnit, Dimension declared) {
            if (!thisUnit.isCommensurable(declared))
                throw new IncommensurableDimensionException("Attempting to create unit with dimension [" + thisUnit.getComposition().toString() + "] but was declared with dimension [" + declared.getComposition().toString() + "].");
        }
        
        private static String calcCompoundName(Map<Unit,Exponent> m) {
            StringBuilder sb = new StringBuilder();
            Iterator<Entry<Unit,Exponent>> iter = m.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<Unit,Exponent> en = iter.next();
                Unit u = en.getKey();
                Exponent e = en.getValue();
                if (!e.equals(Exponents.ZERO)) {
                    sb.append("[");
                    sb.append(u.getName());
                    if (!e.isEqualTo(Exponents.ONE)) {
                        sb.append("^").append(e.numerator());
                        if (e.denominator() != 1) {
                            sb.append("/").append(e.denominator());
                        }
                    }
                    sb.append("]");
                    if (iter.hasNext()) {
                        sb.append(" ");
                    }
                }
            }
            return sb.toString();
        }
        
        private static String calcCompoundSymbol(Map<Unit,Exponent> m) {
            StringBuilder sb = new StringBuilder();
            Iterator<Entry<Unit,Exponent>> iter = m.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<Unit,Exponent> en = iter.next();
                Unit u = en.getKey();
                Exponent e = en.getValue();
                if (!e.equals(Exponents.ZERO)) {
                    sb.append("[");
                    sb.append(u.getSymbol()); 
                    if (!e.isEqualTo(Exponents.ONE)) {
                        sb.append("^").append(e.numerator());
                        if (e.denominator() != 1) {
                            sb.append("/").append(e.denominator());
                        }
                    }
                    sb.append("]");
                    if (iter.hasNext()) {
                        sb.append(" ");
                    }
                }
            }
            return sb.toString();
        }
        
        private Unit createRatioUnit() {
            if (dimension != null) 
                checkDimension(refUnit.getDimension(),dimension);
            Scalar s = scale.multiply(refUnit.getScale());
            String nn = isBlank(name) ? calcRatioName(scale,refUnit) : name;
            String ss = isBlank(symbol) ? calcRatioSymbol(scale,refUnit) : symbol;
            return new UnitImpl(refUnit.getDimension(),s,nn,ss);
        }
        
        private static String calcRatioName(Scalar s,Unit u) {
            StringBuilder sb = new StringBuilder(s.toString().length() + u.getName().length() + 5); //magic number
            sb.append("{(").append(s.toString()).append(") ").append(u.getName()).append("}");
            return sb.toString();
        }
        
        private static String calcRatioSymbol(Scalar s,Unit u) {
            StringBuilder sb = new StringBuilder(s.toString().length() + u.getSymbol().length() + 5); //magic number
            sb.append("{(").append(s.toString()).append(") ").append(u.getSymbol()).append("}");
            return sb.toString();
        }
    }
    
    private static abstract class AbstractUnitBuilder 
            implements UnitBuilder.Dimensioned,
                       UnitBuilder.Referenced,
                       UnitBuilder.Scaled
    {
        protected final UnitBuilderHelper ubh;
        protected AbstractUnitBuilder(UnitBuilderHelper ubh) {
            this.ubh = ubh;
        }

        @Override
        public final Dimension getDimension() {
            return ubh.dimension;
        }

        @Override
        public Unit getReferencedUnit() {
            return ubh.refUnit;
        }

        @Override
        public Scalar getScale() {
            return ubh.scale;
        }
    }
    
    private static final class UnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder 
    {   
        private UnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public DimensionedInitialUnitBuilder ofDimension(Dimension d) {
            return new DimensionedInitialUnitBuilderImpl(ubh.setDimension(d));
        }

        @Override
        public CompoundUnitBuilder as(Unit u) {
            return as(u,1); //magic number
        }

        @Override
        public CompoundUnitBuilder as(Unit u,int exponentNumerator) {
            return as(u,exponentNumerator,1); //magic numver
        }

        @Override
        public CompoundUnitBuilder as(Unit u,int exponentNumerator, int exponentDenominator) {
            return as(u,Exponents.of(exponentNumerator,exponentDenominator));
        }
        
        @Override
        public CompoundUnitBuilder as(Unit u,Exponent e) {
            return new CompoundUnitBuilderImpl(ubh.put(u,e));
        }

        @Override
        public RatioUnitBuilder asTheRatio(int numerator) {
            return asTheRatio(Expressions.take(numerator));
        }

        @Override
        public RatioUnitBuilder asTheRatio(String numerator) {
            return asTheRatio(Expressions.take(numerator));
        }
        
        @Override
        public RatioUnitBuilder asTheRatio(Scalar numerator) {
            return new RatioUnitBuilderImpl(ubh.duplicate(), numerator);
        }

        @Override
        public TransformationUnitBuilder asExactly(int scalar) {
            return asExactly(Expressions.take(scalar));
        }

        @Override
        public TransformationUnitBuilder asExactly(String scalar) {
            return asExactly(Expressions.take(scalar));
        }
        
        @Override
        public TransformationUnitBuilder asExactly(Scalar scalar) {
            return new TransformationUnitBuilderImpl(ubh.setScale(scalar));
        }
    }
    
    private static final class CompoundUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.CompoundUnitBuilder {

        private CompoundUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }
        
        @Override
        public UnitBuilder.CompoundUnitBuilder multiply(Unit u) {
            return multiply(u,1); //magic number
        }

        @Override
        public UnitBuilder.CompoundUnitBuilder multiply(Unit u,int exponentNumerator) {
            return multiply(u,exponentNumerator,1); //magic number
        }
        
        @Override
        public UnitBuilder.CompoundUnitBuilder multiply(Unit u,int exponentNumerator, int exponentDenominator) {
            return multiply(u,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public UnitBuilder.CompoundUnitBuilder multiply(Unit u,Exponent e) {
            return new CompoundUnitBuilderImpl(ubh.put(u,e));
        }

        @Override
        public UnitBuilder.CompoundUnitBuilder divide(Unit u) {
            return divide(u,1); //magic number
        }

        @Override
        public UnitBuilder.CompoundUnitBuilder divide(Unit u,
                                                      int exponentNumerator) {
            return divide(u,exponentNumerator,1); //magic number
        }
        
        @Override
        public UnitBuilder.CompoundUnitBuilder divide(Unit u,
                                                      int exponentNumerator,
                                                      int exponentDenominator) {
            return divide(u,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public UnitBuilder.CompoundUnitBuilder divide(Unit u,Exponent e) {
            return multiply(u,Exponents.negate(e));
        }

        @Override
        public UnitBuilder.CreateableUnitBuilder withName(String name) {
            return new CreateableUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.CreateableUnitBuilder withSymbol(String symbol) {
            return new CreateableUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class CreateableUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.CreateableUnitBuilder {
        
        private CreateableUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.CreateableUnitBuilder withName(String name) {
            return new CreateableUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.CreateableUnitBuilder withSymbol(String symbol) {
            return new CreateableUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class RatioUnitBuilderImpl
            extends AbstractUnitBuilder 
            implements UnitBuilder.RatioUnitBuilder {
        
        private final Scalar numerator;
        
        private RatioUnitBuilderImpl(UnitBuilderHelper ubh, Scalar numerator) {
            super(ubh);
            this.numerator = numerator;
        }

        @Override
        public UnitBuilder.TransformationUnitBuilder over(int denominator) {
            return over(Expressions.take(denominator));
        }

        @Override
        public UnitBuilder.TransformationUnitBuilder over(String denominator) {
            return over(Expressions.take(denominator));
        }
        
        @Override
        public UnitBuilder.TransformationUnitBuilder over(Scalar denominator) {
            return new TransformationUnitBuilderImpl(ubh.setScale(numerator.divide(denominator)));
        }
    }
    
    private static final class TransformationUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.TransformationUnitBuilder {
        
        private TransformationUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.ReferencedTransformationUnitBuilder ofA(Unit u) {
            return new ReferencedTransformationUnitBuilderImpl(ubh.setUnit(u));
        }
    }
    
    private static final class ReferencedTransformationUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.ReferencedTransformationUnitBuilder {
        
        private ReferencedTransformationUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.ReferencedTransformationUnitBuilder withName(String name) {
            return new ReferencedTransformationUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.ReferencedTransformationUnitBuilder withSymbol(String symbol) {
            return new ReferencedTransformationUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class DimensionedInitialUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.DimensionedInitialUnitBuilder 
    {   
        private DimensionedInitialUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder as(Unit u) {
            return as(u,1); //magic number
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder as(Unit u,int exponentNumerator) {
            return as(u,exponentNumerator,1); //magic number
        }
        
        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder as(Unit u,int exponentNumerator, int exponentDenominator) {
            return as(u,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder as(Unit u,Exponent e) {
            return new CompoundDimensionedUnitBuilderImpl(ubh.put(u,e));
        }

        @Override
        public UnitBuilder.RatioDimensionedUnitBuilder asTheRatio(int numerator) {
            return asTheRatio(Expressions.take(numerator));
        }

        @Override
        public UnitBuilder.RatioDimensionedUnitBuilder asTheRatio(String numerator) {
            return asTheRatio(Expressions.take(numerator));
        }
        
        @Override
        public UnitBuilder.RatioDimensionedUnitBuilder asTheRatio(Scalar numerator) {
            return new RatioDimensionedUnitBuilderImpl(ubh.duplicate(), numerator);
        }

        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder asExactly(int scalar) {
            return asExactly(Expressions.take(scalar));
        }

        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder asExactly(String scalar) {
            return asExactly(Expressions.take(scalar));
        }
        
        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder asExactly(Scalar scalar) {
            return new TransformationDimensionedUnitBuilderImpl(ubh.setScale(scalar));
        }
    }
    
    private static final class CompoundDimensionedUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.CompoundDimensionedUnitBuilder {

        private CompoundDimensionedUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }
        
        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder multiply(Unit u) {
            return multiply(u,1); //magic number
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder multiply(Unit u,int exponentNumerator) {
            return multiply(u,exponentNumerator,1); //magic number
        }
        
        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder multiply(Unit u,int exponentNumerator, int exponentDenominator) {
            return multiply(u,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder multiply(Unit u,Exponent e) {
            return new CompoundDimensionedUnitBuilderImpl(ubh.put(u,e));
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder divide(Unit u) {
            return divide(u,1); //magic number
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder divide(Unit u,
                                                      int exponentNumerator) {
            return divide(u,exponentNumerator,1); //magic number
        }
        
        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder divide(Unit u,
                                                      int exponentNumerator,
                                                      int exponentDenominator) {
            return divide(u,Exponents.of(exponentNumerator,exponentDenominator));
        }

        @Override
        public UnitBuilder.CompoundDimensionedUnitBuilder divide(Unit u,Exponent e) {
            return multiply(u,Exponents.negate(e));
        }
        
        @Override
        public UnitBuilder.CreateableDimensionedUnitBuilder withName(String name) {
            return new CreateableDimensionedUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.CreateableDimensionedUnitBuilder withSymbol(String symbol) {
            return new CreateableDimensionedUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class CreateableDimensionedUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.CreateableDimensionedUnitBuilder {
        
        private CreateableDimensionedUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.CreateableDimensionedUnitBuilder withName(String name) {
            return new CreateableDimensionedUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.CreateableDimensionedUnitBuilder withSymbol(String symbol) {
            return new CreateableDimensionedUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class RatioDimensionedUnitBuilderImpl
            extends AbstractUnitBuilder 
            implements UnitBuilder.RatioDimensionedUnitBuilder {
        
        private final Scalar numerator;
        
        private RatioDimensionedUnitBuilderImpl(UnitBuilderHelper ubh, Scalar numerator) {
            super(ubh);
            this.numerator = numerator;
        }

        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder over(int denominator) {
            return over(Expressions.take(denominator));
        }

        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder over(String denominator) {
            return over(Expressions.take(denominator));
        }
        
        @Override
        public UnitBuilder.TransformationDimensionedUnitBuilder over(Scalar denominator) {
            return new TransformationDimensionedUnitBuilderImpl(ubh.setScale(numerator.divide(denominator)));
        }
    }
    
    private static final class TransformationDimensionedUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.TransformationDimensionedUnitBuilder {
        
        private TransformationDimensionedUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.ReferencedTransformationDimensionedUnitBuilder ofA(Unit u) {
            return new ReferencedTransformationDimensionedUnitBuilderImpl(ubh.setUnit(u));
        }
    }
    
    private static final class ReferencedTransformationDimensionedUnitBuilderImpl
            extends AbstractUnitBuilder
            implements UnitBuilder.ReferencedTransformationDimensionedUnitBuilder {
        
        private ReferencedTransformationDimensionedUnitBuilderImpl(UnitBuilderHelper ubh) {
            super(ubh);
        }

        @Override
        public UnitBuilder.ReferencedTransformationDimensionedUnitBuilder withName(String name) {
            return new ReferencedTransformationDimensionedUnitBuilderImpl(ubh.setName(name));
        }

        @Override
        public UnitBuilder.ReferencedTransformationDimensionedUnitBuilder withSymbol(String symbol) {
            return new ReferencedTransformationDimensionedUnitBuilderImpl(ubh.setSymbol(symbol));
        }

        @Override
        public Unit create() throws IncommensurableDimensionException {
            return ubh.create();
        }
    }
    
    private static final class UnitImpl 
            extends AbstractUnit
            implements Unit {

        private final Dimension dimension;
        private final Scalar scale;
        
        private UnitImpl(Dimension dimension, Scalar scale, String name, String symbol) {
            super(name,symbol);
            this.dimension = dimension;
            this.scale = scale;
        }
        
        @Override
        public Dimension getDimension() {
            return dimension;
        }

        @Override
        public Scalar getScale() {
            return scale;
        }
        
        @Override
        public final String toString() {
            return "Unit: " + getName() + " (" + getSymbol() + ")";
        }
    }
}
