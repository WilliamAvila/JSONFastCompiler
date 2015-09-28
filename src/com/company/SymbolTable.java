package com.company;

import com.company.Attributes.SemanticException;
import com.company.Types.Type;
import com.company.Values.InterpretedValue;

import java.util.HashMap;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class SymbolTable {
    public static SymbolTable Instance(){

        if(_instance==null)
            _instance=new SymbolTable();
        return _instance;

    }
    private static SymbolTable _instance = null;
    private HashMap<String, Type> _variables;
    private HashMap<String, InterpretedValue> _values;
    private SymbolTable()
    {
        _variables=new HashMap<>();
        _values=new HashMap<String, InterpretedValue>();
    }

    public Type GetVariableType(String name) throws  SemanticException {
        if (!_variables.containsKey(name))
            throw new SemanticException("Variable "+name+" doesn't exist");
        return _variables.get(name);
    }

    public void DeclareVariable(String name, Type value)throws SemanticException
    {
        if(_variables.containsKey(name))
            throw new SemanticException("Variable " + name + " already exist");
        _variables.put(name,value);


    }

    public InterpretedValue GetVariableValue(String name)
    {
        return _values.get(name);
    }

    public void SetVariableValue(String name, InterpretedValue value)
    {
        _values.put(name,value);
    }

}
