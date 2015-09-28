package com.company.Types;

import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public abstract class Type {
    public String Name;
    public abstract InterpretedValue GetDefaultValue();
    public abstract Type GetType();
}
