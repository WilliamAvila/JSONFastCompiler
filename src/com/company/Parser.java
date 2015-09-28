package com.company;

import com.company.Attributes.*;
import com.company.Factors.FactorValue;
import com.company.Factors.JSONFactorValue;
import com.company.Factors.NumFactorValue;
import com.company.Factors.StringFactorValue;
import com.company.Literals.IntLiteral;
import com.company.Literals.StringLiteral;
import com.company.Operations.*;
import jdk.nashorn.internal.runtime.ParserException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WilliamAvila on 26/09/2015.
 */
public class Parser {
    Lexer lexer;
    Token currenttoken;
    Object value;
    Parser(Lexer lexer){
        this.lexer=lexer;
        this.ConsumeToken();
    }

    void ConsumeToken(){
        currenttoken = lexer.GetToken();
    }

    List<JSONNode> Parse() throws ParserException, SemanticException {
        List<JSONNode> jsonList = JSONList();
        if(currenttoken.type != TokenType.Eof)
            throw new ParserException("Se esperaba Eof");
        return jsonList;
    }

    private List<JSONNode> JSONList() throws ParserException, SemanticException {
        if (currenttoken.type == TokenType.LlaveInicio) {
            JSONNode node = JSON();
            List<JSONNode> jsonNodeList = new ArrayList<>();
            jsonNodeList.add(node);
            return JSONListPrima(jsonNodeList);
        }

        return null;
    }

    private List<JSONNode> JSONListPrima(List<JSONNode> jsonNodeList) throws ParserException, SemanticException {
        if(currenttoken.type == TokenType.Coma) {
            ConsumeToken();
            if(currenttoken.type == TokenType.LlaveInicio) {
                JSONNode node = JSON();
                jsonNodeList.add(node);
                return JSONListPrima(jsonNodeList);
            }
        }

        return jsonNodeList;
    }

    private JSONNode JSON() throws ParserException, SemanticException {
        if(currenttoken.type != TokenType.LlaveInicio)
            throw new ParserException("Se esperaba Llave Inicio");
        ConsumeToken();
        List<Attribute> attributeList = ListaAtributos();
        if(currenttoken.type != TokenType.LlaveFin){
            throw new ParserException("Se esperaba Llave fin");
        }
        ConsumeToken();
        return new JSONNode(attributeList);
    }

    private List<Attribute> ListaAtributos() throws ParserException, SemanticException {
        if(currenttoken.type == TokenType.Assign || currenttoken.type == TokenType.Print ||
                currenttoken.type == TokenType.For || currenttoken.type == TokenType.If){
            Attribute att = Atributo();
            List<Attribute> attributeList = new ArrayList<>();
            attributeList.add(att);
            return ListaAtributoPrima(attributeList);
        }else{
            //epsilon
            return null;
        }
    }

    private List<Attribute> ListaAtributoPrima(List<Attribute> attributeList) throws ParserException, SemanticException {
        if(currenttoken.type == TokenType.Coma) {
            ConsumeToken();
            Attribute att = Atributo();
            attributeList.add(att);
            return ListaAtributoPrima(attributeList);
        }
        return attributeList;
    }

    private Attribute Atributo() throws ParserException, SemanticException {
        if(currenttoken.type == TokenType.Cadena) {

            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            if (currenttoken.type == TokenType.LlaveInicio || currenttoken.type == TokenType.CorcheteInicio || currenttoken.type == TokenType.Cadena
                    || currenttoken.type == TokenType.Numero || currenttoken.type == TokenType.Bool)
                Factor();
            else {
                throw new ParserException("Se esperaba Llave incio, Corchete inicio, Cadena, Numero o Booleano");

            }

        }else if(currenttoken.type == TokenType.Print){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Iniicio");

            }
            ConsumeToken();

            ValueAttribute val = Value();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            return  new PrintAttribute(val);

        }else if(currenttoken.type == TokenType.Assign){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Iniicio");

            }
            ConsumeToken();
            VariableAttribute var = Variable();



            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();
            ValueAttribute value = Value();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            SymbolTable.Instance().DeclareVariable(var.Name,value.type);
            return  new AssignAttribute(var,value);

        }else if(currenttoken.type == TokenType.If){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Iniicio");

            }
            ConsumeToken();

            ValueAttribute condition = Condition();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();



            JSONNode code = Code();

            return  new IfAttribute(condition,code);



        }else if(currenttoken.type == TokenType.For){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Iniicio");

            }
            ConsumeToken();

            ValueAttribute variable = Value();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();

            if(currenttoken.type != TokenType.InitialValue)
                throw new ParserException("Se esperaba Valor Inicial");

            ValueAttribute init = Value();

            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();

            if(currenttoken.type != TokenType.FinalValue)
                throw new ParserException("Se esperaba Valor Final");

            ValueAttribute fin = Value();

            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();

            JSONNode code = Code();

            return  new ForAttribute(variable,init,fin,code);
        }

        return null;
    }
    JSONNode Code() throws SemanticException {
        ConsumeToken();
        if (currenttoken.type != TokenType.DosPuntos) {
            throw new ParserException("Se esperaba Dos Puntos");

        }
        ConsumeToken();


        JSONNode code = JSON();
        if (currenttoken.type != TokenType.LlaveFin) {
            throw new ParserException("Se esperaba Llave Fin");

        }
        ConsumeToken();

        return code;
    }
    ValueAttribute Condition(){
        ConsumeToken();
        if (currenttoken.type != TokenType.DosPuntos) {
            throw new ParserException("Se esperaba Dos Puntos");

        }
        ConsumeToken();
        if (currenttoken.type != TokenType.LlaveInicio) {
            throw new ParserException("Se esperaba Llave Iniicio");

        }
        ConsumeToken();

        ValueAttribute val1 = Value();
        if (currenttoken.type != TokenType.Coma) {
            throw new ParserException("Se esperaba Coma");

        }
        ConsumeToken();
        OperatorValue op = Operator();
        if (currenttoken.type != TokenType.Coma) {
            throw new ParserException("Se esperaba Coma");

        }
        ConsumeToken();
        ValueAttribute val2 = Value();

        if (currenttoken.type != TokenType.LlaveFin) {
            throw new ParserException("Se esperaba Llave Fin");

        }
        ConsumeToken();

        return new ConditionAttribute(val1,op,val2);
    }
    OperatorValue Operator(){
        if(currenttoken.type == TokenType.Operator){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");

            }
            ConsumeToken();
            String op = currenttoken.lexeme;
            ConsumeToken();

            return new OperatorValue(op);
        }
        return null;
    }

    ValueAttribute Value(){
       if(currenttoken.type == TokenType.Value || currenttoken.type == TokenType.InitialValue ||
               currenttoken.type == TokenType.FinalValue) {
           ConsumeToken();
           if (currenttoken.type != TokenType.DosPuntos) {
               throw new ParserException("Se esperaba Dos Puntos");

           }
           ConsumeToken();
           if (currenttoken.type != TokenType.LlaveInicio) {
               throw new ParserException("Se esperaba Llave Inicio");

           }
           ConsumeToken();

           ValueAttribute op= Operation();

           if (currenttoken.type != TokenType.LlaveFin) {
               throw new ParserException("Se esperaba Llave Fin");

           }
           ConsumeToken();
           return op;
        }
        return  null;
    }

    ValueAttribute Operation(){
        if(currenttoken.type == TokenType.Variable){
            VariableAttribute var = Variable();
            return var;

        }
        else if (currenttoken.type == TokenType.Sum) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            ValueAttribute val = Operation();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();
            ValueAttribute val2 = Operation();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();
            return new SumAttribute(val, val2);


        } else if (currenttoken.type == TokenType.Multiply) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            ValueAttribute val = Operation();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();
            ValueAttribute val2 = Operation();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();
            return new MultiplyAttribute(val, val2);


        }else if (currenttoken.type == TokenType.Subtraction) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            ValueAttribute val = Operation();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();
            ValueAttribute val2 = Operation();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();
            return new SubtractionAttribute(val, val2);


        }else if (currenttoken.type == TokenType.Division) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            if (currenttoken.type != TokenType.LlaveInicio) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();

            ValueAttribute val = Operation();
            if (currenttoken.type != TokenType.Coma) {
                throw new ParserException("Se esperaba Coma");

            }
            ConsumeToken();
            ValueAttribute val2 = Operation();
            if (currenttoken.type != TokenType.LlaveFin) {
                throw new ParserException("Se esperaba Llave Fin");

            }
            ConsumeToken();
            return new DivisionAttribute(val, val2);


        } else if (currenttoken.type == TokenType.String_Literal) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();

            StringLiteral stringLit = new StringLiteral(currenttoken.lexeme);
            ConsumeToken();
            return stringLit;


        } else if (currenttoken.type == TokenType.Int_Literal) {
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            IntLiteral intLit = new IntLiteral(Integer.parseInt(currenttoken.lexeme));
            ConsumeToken();
            return intLit;
        }else {
            return  null;
        }
    }


    VariableAttribute Variable(){
        if(currenttoken.type == TokenType.Variable){
            ConsumeToken();
            if (currenttoken.type != TokenType.DosPuntos) {
                throw new ParserException("Se esperaba Dos Puntos");
            }
            ConsumeToken();
            String name = currenttoken.lexeme;
            ConsumeToken();
            return new VariableAttribute(name);
        }else{
            return null;
        }

    }

    FactorValue Factor () throws ParserException, SemanticException {
        if(currenttoken.type == TokenType.LlaveInicio) {
            JSONNode node = JSON();
            return  new JSONFactorValue(node);
        }
        else if(currenttoken.type == TokenType.Cadena) {
            StringFactorValue cadena = new StringFactorValue(currenttoken.lexeme);
            ConsumeToken();
            return  cadena;

        }else if(currenttoken.type == TokenType.Numero) {
            NumFactorValue num = new NumFactorValue(Integer.parseInt(currenttoken.lexeme));

            ConsumeToken();
            return  num;

        }else {
            ConsumeToken();
            return null;
        }
    }

    private void Arreglo() throws ParserException, SemanticException {
        if(currenttoken.type!=TokenType.CorcheteInicio){
            throw new ParserException("Se esperaba Corchete Inicio");
        }
        ConsumeToken();
        JSONList();
        if(currenttoken.type!=TokenType.CorcheteFin){
            throw new ParserException("Se esperaba Corchete Fin");
        }
        ConsumeToken();
    }


}
