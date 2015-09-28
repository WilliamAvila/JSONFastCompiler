package com.company;

/**
 * Created by WilliamAvila on 26/09/2015.
 */
public class Token {
    String lexeme;
    TokenType type;
    public Token(TokenType type,String lexeme){
        this.lexeme=lexeme;
        this.type=type;
    }

    @Override
    public String toString(){
        return "Type: "+ type.toString() +"\nLexeme: "+lexeme+"\n";
    }
}
