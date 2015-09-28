package com.company;

import com.company.Attributes.SemanticException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) {
	    /* Start the parser */
        try {
            String content = readFile("src/test.txt", Charset.defaultCharset());

            Lexer lex = new Lexer(content);
            Token  currentToken = lex.GetToken();
               try{
               while (currentToken.type != TokenType.Eof)
               {
                   System.out.println(currentToken.toString());
                   currentToken = lex.GetToken();
               }
                   System.out.println(currentToken.toString());

                }catch(Exception e){
                   System.out.println("Lexical Exception caught");
                   System.out.println(e.getMessage());
               }
            Parser p = new Parser(new Lexer(content));

            List<JSONNode> result =  p.Parse();

            for(JSONNode node:result)
                node.ValidateSemantic();

            for(JSONNode node:result)
                node.Interpret();

        } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        } catch (SemanticException e) {
            e.printStackTrace();
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
