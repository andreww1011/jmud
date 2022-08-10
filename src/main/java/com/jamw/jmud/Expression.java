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
public interface Expression {
    
    Dimension getDimension();
    
    Expression add(int value, Unit unit) throws IncommensurableDimensionException;
    
    Expression add(String value, Unit unit) throws IncommensurableDimensionException;
    
    Expression add(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> add(T value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> add(Measure<T> measure) throws IncommensurableDimensionException;
    
    Expression add(Expression expression) throws IncommensurableDimensionException;
    
    Expression subtract(int value, Unit unit) throws IncommensurableDimensionException;
    
    Expression subtract(String value, Unit unit) throws IncommensurableDimensionException;
    
    Expression subtract(Scalar value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> subtract(T value, Unit unit) throws IncommensurableDimensionException;
    
    <T extends Field<T>> Measure<T> subtract(Measure<T> measure) throws IncommensurableDimensionException;
    
    Expression subtract(Expression expression) throws IncommensurableDimensionException;
    
    Expression multiply(int scalar);
    
    Expression multiply(String scalar);
    
    Expression multiply(Scalar scalar);
    
    <T extends Field<T>> Measure<T> multiply(T scalar);
    
    Expression multiply(int value, Unit unit);
    
    Expression multiply(String value, Unit unit);
    
    Expression multiply(Scalar value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(T value, Unit unit);
    
    <T extends Field<T>> Measure<T> multiply(Measure<T> measure);
    
    Expression multiply(Expression expression);
    
    Expression divide(int scalar) throws ArithmeticException;
    
    Expression divide(String scalar) throws ArithmeticException;
    
    Expression divide(Scalar scalar) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T scalar) throws ArithmeticException;
    
    Expression divide(int value, Unit unit) throws ArithmeticException;
    
    Expression divide(String value, Unit unit) throws ArithmeticException;
    
    Expression divide(Scalar value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(T value, Unit unit) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> divide(Measure<T> measure) throws ArithmeticException;
    
    Expression divide(Expression expression) throws ArithmeticException;
    
    <T extends Field<T>> Measure<T> using(T.Factory<T> factory);
}
