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
public interface Scale {
    
    String getName();
    
    String getSymbol();
    
    Unit getReferenceUnit();
    
    <T extends Field<T>> Magnitude<T> of(T value);
        
    <T extends Field<T>> Magnitude<T> magnitude(Measure<T> measure);
        
    @Override
    String toString();
}
