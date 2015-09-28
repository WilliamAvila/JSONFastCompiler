package com.company.Operations;

import com.company.Attributes.SemanticException;
import com.company.OperatorValue;
import com.company.Types.IntType;
import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.Values.IntValue;
import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class ConditionAttribute extends ValueAttribute{
    ValueAttribute leftChild;
    OperatorValue operator;
    ValueAttribute rightChild;
    public ConditionAttribute(ValueAttribute val1, OperatorValue op, ValueAttribute val2) {
        this.leftChild = val1;
        this.rightChild = val2;
        this.operator = op;
    }

    @Override
    public Type ValidateSemantic() throws SemanticException {
        if(!(leftChild.ValidateSemantic() instanceof IntType && rightChild.ValidateSemantic() instanceof  IntType))
            throw new SemanticException("Comparacion entre tipos no soportada");
        return new IntType();
    }

    @Override
    public InterpretedValue Evaluate() {
        int result =0;
        IntValue leftValue = (IntValue)leftChild.Evaluate();
        IntValue rightValue = (IntValue)rightChild.Evaluate();
        if(operator.operatorName.equals("==")){
            if(leftValue.value == rightValue.value)
                result = 1;
        }else if(operator.operatorName.equals(">=")){
            if(leftValue.value >= rightValue.value)
                result = 1;
        }else if(operator.operatorName.equals("<=")){
            if(leftValue.value <= rightValue.value)
                result = 1;
        }else if(operator.operatorName.equals("!=")){
            if(leftValue.value != rightValue.value)
                result = 1;
        }else if(operator.operatorName.equals(">")){
            if(leftValue.value > rightValue.value)
                result = 1;
        }else if(operator.operatorName.equals("<")){
            if(leftValue.value < rightValue.value)
                result = 1;
        }


        return new IntValue(result);

    }
}
