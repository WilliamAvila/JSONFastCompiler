package com.company;

import com.company.Attributes.SemanticException;
import com.company.Types.Type;

import java.util.HashMap;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public abstract class BinaryOperationNode extends ValueAttribute {
    public BinaryOperationNode(ValueAttribute leftNode, ValueAttribute rightNode)
    {
        LeftNode = leftNode;
        RightNode = rightNode;
    }

    public HashMap<String, Type> Rules ;

    public ValueAttribute RightNode;

    public ValueAttribute LeftNode;


    public Type ValidateSemantic() throws SemanticException {
        Type rightType = RightNode.ValidateSemantic();
        Type leftType = LeftNode.ValidateSemantic();
        String pair = leftType.GetType().Name+"x" + rightType.GetType().Name;
        if(Rules.containsKey(pair))
        {
            return Rules.get(pair);
        }
        else
        {
            throw  new SemanticException("Tipo es incompatible");
        }
    }

}
