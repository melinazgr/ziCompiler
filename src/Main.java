import java.io.*;
import java_cup.runtime.ComplexSymbolFactory;

public class Main {
    public static void main(String[] argv)
    {
        System.out.println("Starting program: " + argv[0]);
        /* Start the parser */
        try {
        parser p = new parser(new Lexer(new FileReader(argv[0])));
        Object result = p.parse().value;
        } catch (Exception e) {
        /* do cleanup here -- possibly rethrow e */
        e.printStackTrace();
        }
    }
    }