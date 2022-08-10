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
public interface Comparable<T> extends java.lang.Comparable<T> {
    
    default public boolean isEqualTo(T t) {
        return this.compareTo(t) == 0;
    }
    
    default public boolean isLessThan(T t) {
        return this.compareTo(t) < 0;
    }
    
    default public boolean isGreaterThan(T t) {
        return this.compareTo(t) > 0;
    }
}
