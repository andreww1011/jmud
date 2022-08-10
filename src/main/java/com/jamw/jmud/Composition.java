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
public interface Composition extends Iterable<CompositionComponent> {
    
    Exponent getExponent(FundamentalDimension d);
    
    boolean equals(Object o);
}
