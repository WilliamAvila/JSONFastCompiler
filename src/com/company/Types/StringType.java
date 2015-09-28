package com.company.Types;

import com.company.Values.InterpretedValue;
import com.company.Values.StringValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class StringType extends  Type {
    public StringType(){
        this.Name = "Cadena";
    }
    @Override
    public InterpretedValue GetDefaultValue() {
        return new StringValue("");
    }

    @Override
    public Type GetType() {
        return new StringType();
    }
}
