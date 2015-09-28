package com.company;

import com.company.Attributes.SemanticException;
import com.company.Types.Type;
import com.company.Values.InterpretedValue;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class VariableAttribute extends ValueAttribute {
    public String Name;

    public VariableAttribute(String name) {
        this.Name = name;
    }

    @Override
    public Type ValidateSemantic() throws SemanticException {
        return SymbolTable.Instance().GetVariableType(Name);
    }

    public InterpretedValue Evaluate()
    {

        return SymbolTable.Instance().GetVariableValue(Name);
    }

    public void SetValue(InterpretedValue value)
    {
        SymbolTable.Instance().SetVariableValue(Name, value);
    }
}
