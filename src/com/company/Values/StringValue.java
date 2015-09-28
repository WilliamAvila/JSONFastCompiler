package com.company.Values;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class StringValue extends InterpretedValue {
    public String value;
    public StringValue(String s) {
        value = s;
    }

    @Override
    public String ValueToString() {
        return value;
    }
}
