package com.company.Attributes;

import com.company.Factors.FactorValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public abstract class Attribute {
    public Attribute(String name, FactorValue factorValue) {
        this.name = name;
        this.factor = factorValue;
    }

    String name;
    FactorValue factor;

    public Attribute() {

    }

    public abstract void  ValidateSemantic() throws SemanticException, Exception;

    public abstract void  Evaluate() throws SemanticException;
}
