package com.company;

import com.company.Attributes.Attribute;
import com.company.Attributes.SemanticException;

import java.util.List;

/**
 * Created by WilliamAvila on 26/09/2015.
 */
public class JSONNode {
    public Object value;
    List<Attribute> attributeList;

    public JSONNode(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public void ValidateSemantic() throws SemanticException, Exception {

        if(attributeList != null) {
            for (Attribute node : attributeList)
                node.ValidateSemantic();
        }
    }

    public void Interpret() throws SemanticException {
        if(attributeList != null) {
            for (Attribute node : attributeList)
                node.Evaluate();
        }
    }
}
