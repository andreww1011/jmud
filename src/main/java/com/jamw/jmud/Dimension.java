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
public interface Dimension {
    
    static void assertCommensurable(Dimension d1, Dimension d2) throws IncommensurableDimensionException {
        if (!d1.isCommensurable(d2))
            throw new IncommensurableDimensionException(d1.getName() + " and " + d2.getName() + " are not commensurable.");
    } 
    
    String getName();
    
    String getSymbol();
    
    Composition getComposition();
    
    default boolean isCommensurable(Dimension d) {
        if (this.equals(d))
            return true;
        else
            return getComposition().equals(d.getComposition());
    }
    
    @Override
    boolean equals(Object o);
    
}
