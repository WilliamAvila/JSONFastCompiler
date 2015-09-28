package com.company.Attributes;

import com.company.ValueAttribute;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class PrintAttribute  extends Attribute {
    ValueAttribute Value;
    public PrintAttribute(ValueAttribute val) {
        this.Value = val;
    }

    @Override
    public void ValidateSemantic() throws SemanticException {


    }

    @Override
    public void Evaluate() throws SemanticException {
        System.out.println(Value.Evaluate().ValueToString());
    }
}
