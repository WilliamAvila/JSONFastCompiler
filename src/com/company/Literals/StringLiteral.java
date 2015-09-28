package com.company.Literals;

import com.company.Types.StringType;
import com.company.Types.Type;
import com.company.ValueAttribute;
import com.company.Values.InterpretedValue;
import com.company.Values.StringValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class StringLiteral extends ValueAttribute{
    String value;
    public StringLiteral(String lexeme) {
        this.value = lexeme;
        this.type = new StringType();
    }

    @Override
    public Type ValidateSemantic() {
        return new StringType();
    }

    @Override
    public InterpretedValue Evaluate() {

        return new StringValue(value.replace("\"",""));
    }
}
