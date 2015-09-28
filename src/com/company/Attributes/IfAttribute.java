package com.company.Attributes;

import com.company.JSONNode;
import com.company.ValueAttribute;
import com.company.Values.IntValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class IfAttribute extends Attribute {
    ValueAttribute condition;
    JSONNode code;

    public IfAttribute(ValueAttribute condition, JSONNode code) {
        this.condition = condition;
        this.code = code;
    }

    @Override
    public void ValidateSemantic() throws SemanticException, Exception {
            condition.ValidateSemantic();
            code.ValidateSemantic();
    }

    @Override
    public void Evaluate() throws SemanticException {
        IntValue cond = (IntValue)condition.Evaluate();
            if(cond.value == 1)
                code.Interpret();
    }


}
