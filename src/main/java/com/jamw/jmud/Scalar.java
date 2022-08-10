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
public interface Scalar {
 
    Scalar negate();
    
    Scalar reciprocal() throws ArithmeticException;
    
    Scalar add(int scalar);
    
    Scalar add(String scalar);
    
    Scalar add(Scalar scalar);
    
    Scalar subtract(int scalar);
    
    Scalar subtract(String scalar);
    
    Scalar subtract(Scalar scalar);
    
    Scalar multiply(int scalar);
    
    Scalar multiply(String scalar);
    
    Scalar multiply(Scalar scalar);
    
    Expression multiply(int value, Unit unit);
    
    Expression multiply(String value, Unit unit);
    
    Expression multiply(Expression expression);
    
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    Scalar divide(int scalar) throws ArithmeticException;
    
    Scalar divide(String scalar) throws ArithmeticException;
    
    Scalar divide(Scalar scalar) throws ArithmeticException;
    
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    Expression divide(Expression expression) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    Scalar power(int exponent) throws ArithmeticException;
    
    Scalar power(String exponent) throws ArithmeticException;
    
    Scalar power(Scalar exponent) throws ArithmeticException;
    
    Scalar logarithm(int base) throws ArithmeticException;
    
    Scalar logarithm(String base) throws ArithmeticException;
    
    Scalar logarithm(Scalar base) throws ArithmeticException;
    
    <T extends Field<T>> T using(T.Factory<T> factory);
}
