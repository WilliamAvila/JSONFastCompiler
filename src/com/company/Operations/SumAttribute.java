package com.company.Operations;

import com.company.Attributes.SemanticException;
import com.company.Types.IntType;
import com.company.Types.StringType;
import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.Values.IntValue;
import com.company.Values.InterpretedValue;
import com.company.Values.StringValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class SumAttribute extends ValueAttribute {
    ValueAttribute leftNode;
    ValueAttribute rightNode;

    public SumAttribute(ValueAttribute leftNode, ValueAttribute rightNode) {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.type = leftNode.type;

    }

    @Override
    public Type ValidateSemantic() throws SemanticException {
        if(leftNode.ValidateSemantic() instanceof IntType && rightNode.ValidateSemantic() instanceof  IntType) {
           return new IntType();

        }
        if(!(leftNode.ValidateSemantic() instanceof StringType && rightNode.ValidateSemantic() instanceof StringType)) {
             return new StringType();
        }
        return null;
    }

    @Override
    public InterpretedValue Evaluate() {

            InterpretedValue leftType = leftNode.Evaluate();
            InterpretedValue righType = rightNode.Evaluate();
            if (leftType instanceof IntValue && righType instanceof IntValue)
            {
                return new IntValue(((IntValue)leftType).value + ((IntValue)righType).value);
            }
            else if (leftType instanceof StringValue && righType instanceof StringValue)
            {
                return new StringValue(((StringValue)leftType).value + ((StringValue)righType).value);
            }
            return null;
        }

}
