package com.company.Operations;

import com.company.Attributes.SemanticException;
import com.company.Types.IntType;
import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.Values.IntValue;
import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class DivisionAttribute extends ValueAttribute {
    ValueAttribute leftNode;
    ValueAttribute rightNode;
    public DivisionAttribute(ValueAttribute val, ValueAttribute val2) {
        this.leftNode = val;
        this.rightNode = val2;
        this.type = leftNode.type;
    }

    @Override
    public Type ValidateSemantic() throws SemanticException {
        if(leftNode.ValidateSemantic() instanceof IntType && rightNode.ValidateSemantic() instanceof  IntType) {
            return new IntType();

        }
        return null;
    }

    @Override
    public InterpretedValue Evaluate() {
        InterpretedValue leftType = leftNode.Evaluate();
        InterpretedValue righType = rightNode.Evaluate();
        if (leftType instanceof IntValue && righType instanceof IntValue)
        {
            return new IntValue(((IntValue)leftType).value / ((IntValue)righType).value);
        }

        return null;
    }
}
