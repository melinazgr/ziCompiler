import Nodes.*;
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
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            parser p = new parser(new Lexer(new FileReader(argv[0]), sf), sf);
            ProgramNode result =(ProgramNode) p.parse().value;

            System.out.println(result.toString());

        } catch (Exception e) {
            /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }

}


    //TODO if else grammar conflict
    //TODO assign grammar conflict
    //TODO parse cmd args