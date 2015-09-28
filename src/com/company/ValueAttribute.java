package com.company;

import com.company.Attributes.SemanticException;
import com.company.Types.Type;
import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public abstract class ValueAttribute {

    public Type type;
    public abstract Type ValidateSemantic() throws SemanticException;
    public abstract InterpretedValue Evaluate();
}
