package com.company.Types;

import com.company.Values.InterpretedValue;
import com.company.Values.IntValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class IntType extends  Type {

    public IntType(){
        this.Name = "Entero";
    }
    @Override
    public InterpretedValue GetDefaultValue() {

        return new IntValue(0);
    }

    @Override
    public Type GetType() {
        return new IntType();
    }
}
