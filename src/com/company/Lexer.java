package com.company;

/**
 * Created by WilliamAvila on 26/09/2015.
 */
public class Lexer {

    private String input;
    private int position;

    public Lexer(String input){
        this.input=input;

    }
    public char GetCurrentSymbol(){
        if (position < input.length())
            return input.charAt(position);
        return '\0';
    }
    public char GetNextSymbol(){
        position++;
        return GetCurrentSymbol();
    }
    public Token GetToken(){
        char symbol = GetCurrentSymbol();
        String lexeme = "";
        int state = 0;
        while(true){
            switch(state){
                case 0:
                    if(symbol == '\0'){
                        return new Token(TokenType.Eof,"");
                    }
                    if(symbol=='{' || symbol=='}'|| symbol=='[' || symbol==']'||symbol==',' || symbol==':') {
                        lexeme+=symbol;
                        state = 1;
                    }
                    else if(Character.isDigit(symbol))
                    {
                        lexeme+=symbol;
                        symbol=GetNextSymbol();
                        state=2;
                    }
                    else if(symbol == '\"'){
                        lexeme+=symbol;
                        symbol=GetNextSymbol();
                        state=3;
                    }
                    else if(symbol=='\''){
                        lexeme+=symbol;
                        symbol=GetNextSymbol();
                        state=4;
                    }
                    else if(Character.isLetter(symbol)){
                        lexeme+=symbol;
                        symbol=GetNextSymbol();
                        state=5;
                    }
                    else if(Character.isWhitespace(symbol) || symbol == '\r' || symbol == '\n')
                        symbol=GetNextSymbol();
                    break;
                case 1:
                    if(symbol=='{'){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.LlaveInicio,lexeme);
                    }
                    else if(symbol=='}'){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.LlaveFin,lexeme);
                    }
                    else if(symbol=='['){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.CorcheteInicio,lexeme);
                    }
                    else if(symbol==']'){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.CorcheteFin,lexeme);
                    }
                    else if(symbol==':'){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.DosPuntos,lexeme);
                    }

                    if(symbol==','){
                        symbol=GetNextSymbol();
                        return new Token(TokenType.Coma,lexeme);
                    }
                    break;
                case 2:
                    if(Character.isDigit(symbol)){
                        lexeme+=symbol;
                        symbol=GetNextSymbol();
                    }
                    else{
                        return new Token(TokenType.Numero,lexeme);
                    }
                    break;
                case 3:
                    if(symbol != '\"') {
                        lexeme += symbol;
                        symbol=GetNextSymbol();
                    }
                    else if(symbol == '\"') {
                        lexeme += symbol;
                        symbol =GetNextSymbol();
                        return new Token(TokenType.Cadena, lexeme);
                    }
                    break;
                case 4:
                    if(symbol != '\'') {
                        lexeme += symbol;
                        symbol=GetNextSymbol();
                    }
                    else if(symbol == '\'') {
                        lexeme += symbol;
                        symbol =GetNextSymbol();
                        return new Token(TokenType.Cadena, lexeme);
                    }
                    break;
                case 5:
                    if(Character.isLetter(symbol)) {
                        lexeme += symbol;
                        symbol=GetNextSymbol();
                    }else {
                        lexeme = lexeme.toLowerCase();
                        if (lexeme.equals("print")) {
                            return new Token(TokenType.Print, lexeme);
                        } else if (lexeme.equals("assign")) {
                            return new Token(TokenType.Assign, lexeme);
                        } else if (lexeme.equals("if")) {
                            return new Token(TokenType.If, lexeme);
                        } else if (lexeme.equals("for")) {
                            return new Token(TokenType.For, lexeme);
                        } else if (lexeme.equals("variable")) {
                            return new Token(TokenType.Variable, lexeme);
                        } else if (lexeme.equals("value")) {
                            return new Token(TokenType.Value, lexeme);
                        } else if (lexeme.equals("sum")) {
                            return new Token(TokenType.Sum, lexeme);
                        } else if (lexeme.equals("multiply")) {
                            return new Token(TokenType.Multiply, lexeme);
                        } else if (lexeme.equals("division")) {
                            return new Token(TokenType.Division, lexeme);
                        } else if (lexeme.equals("subtraction")) {
                            return new Token(TokenType.Subtraction, lexeme);
                        } else if (lexeme.equals("condition")) {
                            return new Token(TokenType.Condition, lexeme);
                        } else if (lexeme.equals("intliteral")) {
                            return new Token(TokenType.Int_Literal, lexeme);
                        } else if (lexeme.equals("stringliteral")) {
                            return new Token(TokenType.String_Literal, lexeme);
                        }else if (lexeme.equals("condition")) {
                            return new Token(TokenType.Condition, lexeme);
                        }else if (lexeme.equals("operator")) {
                            return new Token(TokenType.Operator, lexeme);
                        }else if (lexeme.equals("code")) {
                            return new Token(TokenType.Code, lexeme);
                        }else if (lexeme.equals("initialvalue")) {
                            return new Token(TokenType.InitialValue, lexeme);
                        }else if (lexeme.equals("finalvalue")) {
                            return new Token(TokenType.FinalValue, lexeme);
                        } else {

                        }
                    }

                    break;
            }
        }
    }




}
