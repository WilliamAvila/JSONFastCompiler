package com.company.Attributes;

import com.company.JSONNode;
import com.company.Types.IntType;
import com.company.ValueAttribute;
import com.company.Values.IntValue;
import com.company.VariableAttribute;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class ForAttribute extends Attribute {
    ValueAttribute variable;
    ValueAttribute initialValue;
    ValueAttribute finalValue;
    JSONNode code;

    public ForAttribute(ValueAttribute variable, ValueAttribute init, ValueAttribute fin, JSONNode code) {
        this.variable =variable;
        this.initialValue = init;
        this.finalValue = fin;
        this.code = code;
    }

    @Override
    public void ValidateSemantic() throws SemanticException, Exception {
        if(!(variable.ValidateSemantic() instanceof IntType))
        {
            throw new SemanticException("Se esperaba  variable entero");
        }
        if (!(initialValue.ValidateSemantic() instanceof IntType))
        {
            throw new SemanticException("Se esperaba  Expresion inicial entero");
        }
        if (!(finalValue.ValidateSemantic() instanceof IntType))
        {
            throw new SemanticException("Se esperaba  Expresion final entero");
        }
        code.ValidateSemantic();
    }

    @Override
    public void Evaluate() throws SemanticException {
        VariableAttribute idNode = (VariableAttribute) variable;
        idNode.SetValue(initialValue.Evaluate());
        IntValue  finVal = (IntValue) finalValue.Evaluate();
        while (((IntValue)idNode.Evaluate()).value <= finVal.value)
        {
            code.Interpret();
            idNode.SetValue(new IntValue(((IntValue)idNode.Evaluate()).value+1));
        }
    }
}
