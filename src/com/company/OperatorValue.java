package com.company;

/**
 * Created by WilliamAvila on 27/09/2015.
 */
public class OperatorValue {
    public String operatorName;
    public OperatorValue(String lexeme) {
        this.operatorName = lexeme.replace("'","");
    }
}
