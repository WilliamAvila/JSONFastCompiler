package com.company.Attributes;

import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.VariableAttribute;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class AssignAttribute extends Attribute{
    VariableAttribute variable;
    ValueAttribute value;
    public AssignAttribute(VariableAttribute var, ValueAttribute val) {
        super();
        this.value = val;
        this.variable = var;
    }

    public  void ValidateSemantic() throws SemanticException, Exception {

        Type variableType= variable.ValidateSemantic();
        Type valueType = value.ValidateSemantic();
        if (variableType.GetType().Name!=valueType.GetType().Name)
        {
            throw new SemanticException("Tipos incompatibles entre si.");
        }
    }

    @Override
    public void Evaluate() throws SemanticException {
        VariableAttribute idNode =  variable;
        idNode.SetValue(value.Evaluate());
    }
}
