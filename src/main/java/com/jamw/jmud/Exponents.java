/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamw.jmud;

/**
 *
 * @author Andrew
 */
public abstract class Exponents {
    
    private Exponents() {}
    
    /**
     * The rational number power zero (0/1).
     */
    public static final Exponent ZERO = of(0,1);
    
    /**
     * The rational number power one (1/1).
     */
    public static final Exponent ONE = of(1,1);
    
    /**
     * The rational number power two (2/1).
     */
    public static final Exponent SQUARED = of(2,1);
    
    /**
     * The rational number power three (3/1).
     */
    public static final Exponent CUBED = of(3,1);
    
    /**
     * The rational number power one-half (1/2).
     */
    public static final Exponent SQUARE_ROOT = of(1,2);
    
    /**
     * The rational number power one-third (1/3).
     */
    public static final Exponent CUBE_ROOT = of(1,3);
    
    /**
     * The rational number power negative-one (-1/1).
     */
    public static final Exponent INVERSE = of(-1,1);
    
    public static final Exponent of(int numerator) {
        return of(numerator,1); //magic number
    }
    
    public static final Exponent of(int numerator, int denominator) {
        return of(numerator,denominator, false);
    }
    
    private static final Exponent of(int numerator, int denominator, boolean isReduced) {
        return new ExponentImpl(numerator,denominator, isReduced);
    }
    
    private static final class ExponentImpl implements Exponent {
        
        private final int numerator, denominator;

        private ExponentImpl(int numerator, int denominator) {
            this(numerator,denominator,false);
        }

        private ExponentImpl(int numerator, int denominator, boolean isReduced) {
            if (isReduced) {
                this.numerator = numerator;
                this.denominator = denominator;
            } else {
                checkDenominator(denominator);
                int n = numerator;
                int d = denominator;
                if (denominator < 0) {
                    n = -n;
                    d = -d;
                }
                int gcd = gcd(java.lang.Math.abs(n),d);
                n /= gcd;
                d /= gcd;
                this.numerator = n;
                this.denominator = d;
            }
        }

        private static void checkDenominator(int denominator) {
            if (denominator == 0)
                throw new IllegalArgumentException("Denominator cannot be equal to zero.");
        }

        @Override
        public final int numerator() {
            return numerator;
        }

        @Override
        public final int denominator() {
            return denominator;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Exponent))
                return false;
            Exponent eo = (Exponent)o;
            return this.isEqualTo(eo);
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 89 * hash + this.numerator;
            hash = 89 * hash + this.denominator;
            return hash;
        }

        @Override
        public final int compareTo(Exponent r) {
            long a = (long)this.numerator()*r.denominator();
            long b = (long)r.numerator()*this.denominator();
            return Long.compare(a,b);
        }
        
        @Override
        public final String toString() {
            return numerator + "/" + denominator;
        }
    }
    
    private static int gcd(int a, int b) {
        int A,B,tmp;
        A = a;
        B = b;
        while (B != 0) {
            tmp = A;
            A = B;
            B = tmp%A;
        }
        return A;
    }
    
    /**
     * Utility method to aid the calculation of the rational number exponent
     * of the product of two variables with rational number exponents.  The returned exponent
     * is in lowest terms.
     * <p>
     * <code>X<sup>a</sup> * X<sup>b</sup> = X<sup>a+b</sup></code>
     * @param a exponent
     * @param b exponent
     * @return the rational number exponent a+b in lowest terms
     */
    public static Exponent product(Exponent a, Exponent b) {
        if (ZERO.isEqualTo(a))
            return b;
        if (ZERO.isEqualTo(b))
            return a;
        int ngcd = gcd(a.numerator(),b.numerator());
        int dgcd = gcd(a.denominator(),b.denominator());
        int n = (a.numerator() / ngcd) * (b.denominator() / dgcd) + (b.numerator() / ngcd) * (a.denominator() / dgcd);
        int d = lcm(a.denominator(),b.denominator());
        Exponent e = new ExponentImpl(n,d);
        return new ExponentImpl(e.numerator()*ngcd,e.denominator(),true);
    }
    
    private static int lcm(int a, int b) {
        return a * (b / gcd(a,b));
    }
    
    private static void checkLong(long l) {
        if (l > Integer.MAX_VALUE)
            throw new ArithmeticException("integer overflow");
    }

    /**
     * Utility method to aid the calculation of the rational number exponent
     * of a variable with rational number exponent {@code a} raised to the 
     * power of the rational number exponent {@code b}.  The returned exponent
     * is in lowest terms.
     * <p>
     * <code>(X<sup>a</sup>)<sup>b</sup> = X<sup>a*b</sup></code>
     * @param a exponent
     * @param b exponent
     * @return the rational number exponent a*b in lowest terms
     */
    public static Exponent power(Exponent a, Exponent b) {
        if (ZERO.isEqualTo(a) || ZERO.isEqualTo(b))
            return ZERO;
        if (ONE.isEqualTo(a))
            return b;
        if (ONE.isEqualTo(b))
            return a;
        Exponent e1 = new ExponentImpl(a.numerator(),b.denominator());
        Exponent e2 = new ExponentImpl(b.numerator(),a.denominator());
        return new ExponentImpl(e1.numerator()*e2.numerator(),e1.denominator()*e2.denominator());
    }
    
    /**
     * Utility method to negate a rational number exponent. 
     * @param a exponent
     * @return 
     */
    public static Exponent negate(Exponent a) {
        return new ExponentImpl(-a.numerator(),a.denominator(), true);
    }
}
