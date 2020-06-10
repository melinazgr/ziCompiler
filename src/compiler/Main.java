import Nodes.*;
import Model.*;
import Error.*;
import java.io.*;
import java_cup.runtime.*;

public class Main {

    public static boolean Debug = false;
    public static void main(String[] argv)
    {
        
        System.out.println("Compiling program: " + argv[0]);
        /* Start the parser */
        try 
        {
            SemanticAnalysis semantic = new SemanticAnalysis();
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            parser p = new parser(new Lexer(new FileReader(argv[0]), sf), sf);
            ProgramNode result = (ProgramNode) p.parse().value;

            System.out.println(result.toString());

            result.accept(semantic);

            ErrorHandler errors = ErrorHandler.getInstance();
            errors.printError();
            
        } catch (Exception e) {
            /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }

}

    //TODO if else grammar conflict
    //TODO assign grammar conflict
    //TODO parse cmd args


