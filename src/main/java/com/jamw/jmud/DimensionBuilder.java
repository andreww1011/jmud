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
public interface DimensionBuilder {
    
    DimensionBuilder append(Dimension d);
    
    DimensionBuilder append(Dimension d, int exponentNumerator);
    
    DimensionBuilder append(Dimension d, int exponentNumerator, int exponentDenominator);
    
    DimensionBuilder append(Dimension d, Exponent e);
    
    DimensionBuilder withName(String name);
    
    DimensionBuilder withSymbol(String symbol);
    
    Dimension create();
    
}
