package com.company.Literals;

import com.company.Types.IntType;
import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.Values.IntValue;
import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class IntLiteral extends ValueAttribute {
    int value;
    public IntLiteral(int i) {
        this.value = i;
        this.type = new IntType();
    }

    @Override
    public Type ValidateSemantic() {
        return type;
    }

    @Override
    public InterpretedValue Evaluate() {
        return new IntValue(value);
    }
}
