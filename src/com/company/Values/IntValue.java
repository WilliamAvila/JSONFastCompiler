package com.company.Values;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class IntValue extends InterpretedValue {
    public int value;
    public IntValue(int i) {
        this.value =i;
    }

    @Override
    public String ValueToString() {
        return Integer.toString(value);
    }
}
