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
 *
 * @author andreww1011
 */
public interface UnitBuilder 
        extends InitialUnitBuilder<UnitBuilder.CompoundUnitBuilder,
                                   UnitBuilder.RatioUnitBuilder,
                                   UnitBuilder.TransformationUnitBuilder> {
    
    DimensionedInitialUnitBuilder ofDimension(Dimension d);
    
    interface CompoundUnitBuilder 
            extends Compound<CompoundUnitBuilder>,
                    Createable<CreateableUnitBuilder> {}
    
    interface CreateableUnitBuilder 
            extends Createable<CreateableUnitBuilder> {}
    
    interface RatioUnitBuilder
            extends Ratio<TransformationUnitBuilder> {}
    
    interface TransformationUnitBuilder 
            extends Scaled,
                    Transformation<ReferencedTransformationUnitBuilder> {}
    
    interface ReferencedTransformationUnitBuilder 
            extends Scaled,
                    Referenced,
                    Createable<ReferencedTransformationUnitBuilder> {}
    
    interface DimensionedInitialUnitBuilder 
            extends Dimensioned,
                    InitialUnitBuilder<CompoundDimensionedUnitBuilder,
                                       RatioDimensionedUnitBuilder,
                                       TransformationDimensionedUnitBuilder> {}
    
    interface CompoundDimensionedUnitBuilder 
            extends Dimensioned,
                    Compound<CompoundDimensionedUnitBuilder>,
                    Createable<CreateableDimensionedUnitBuilder> {}
    
    interface CreateableDimensionedUnitBuilder 
            extends Dimensioned,
                    Createable<CreateableDimensionedUnitBuilder> {}
    
    interface RatioDimensionedUnitBuilder 
            extends Dimensioned,
                    Ratio<TransformationDimensionedUnitBuilder> {}
    
    interface TransformationDimensionedUnitBuilder
            extends Dimensioned,
                    Scaled,
                    Transformation<ReferencedTransformationDimensionedUnitBuilder> {}
    
    interface ReferencedTransformationDimensionedUnitBuilder
            extends Dimensioned,
                    Scaled,
                    Referenced,
                    Createable<ReferencedTransformationDimensionedUnitBuilder> {}
    
    interface Dimensioned {
        Dimension getDimension();
    }

    interface Scaled {
        Scalar getScale();
    }

    interface Referenced {
        Unit getReferencedUnit();
    }

    interface Compound<C>
            {
        C multiply(Unit u);
        C multiply(Unit u, int exponentNumerator);
        C multiply(Unit u, int exponentNumerator, int denominator);
        C multiply(Unit u, Exponent e);
        C divide(Unit u);
        C divide(Unit u, int exponentNumerator);
        C divide(Unit u, int exponentNumerator, int denominator);
        C divide(Unit u, Exponent e);
    }
    
    interface Ratio<T> {
        T over(int denominator);
        T over(String denominator);
        T over(Scalar denominator);
    }

    interface Transformation<R> {
        R ofA(Unit u);
    }
    
    interface Createable<X> {
        X withName(String name);
        X withSymbol(String symbol);
        Unit create() throws IncommensurableDimensionException;
    }
}

interface InitialUnitBuilder<C,R,T>{
    C as(Unit u);
    C as(Unit u, int exponentNumerator);
    C as(Unit u, int exponentNumerator, int denominator);
    C as(Unit u, Exponent e);
    R asTheRatio(int numerator);
    R asTheRatio(String numerator);
    R asTheRatio(Scalar numerator);
    T asExactly(int scalar);
    T asExactly(String scalar);
    T asExactly(Scalar scalar);
}