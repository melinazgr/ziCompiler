import Nodes.*;
import Model.*;
import Error.*;
import java.io.*;
import java_cup.runtime.*;

public class Main {

    public static boolean Debug = false;
    public static void main(String[] argv)
    {


        String helpText = "Usage: java Main [-debug] [-ast <ast filename>] [-ir <filename>] -i <src filename>"; 
        String astOutFilename = null;
        String srcInputFilename = null;
        String irOutFilename = null;

        /* Start the parser */
        try 
        {
            for ( int i = 0; i < argv.length; i++ ) {
				if ( argv[ i ].equals( "-debug" ) ) {
					Main.Debug = true;
				}
                else if ( argv[ i ].equals( "-i" ) ) {
					i++;
                    if ( i >= argv.length ){
                        throw new Exception( "Missing filename" );
                    }

					srcInputFilename = argv[ i ];
                }
                else if ( argv[ i ].equals( "-ast" ) ) {
                    i++;
                    
                    if ( i >= argv.length ){
                        throw new Exception( "Missing AST filename" );
                    }

					astOutFilename = argv[ i ];
                }
                                
                else if ( argv[ i ].equals( "-ir" ) ) {
					i++;
                    if ( i >= argv.length ){
                        throw new Exception( "Missing filename" );
                    }

					irOutFilename = argv[ i ];
                }
                
				else {
					throw new Exception(helpText);
                }
            }

            if (srcInputFilename == null){
                throw new Exception(helpText);
            }

            System.out.println("Compiling program: " + srcInputFilename);

            SemanticAnalysis semantic = new SemanticAnalysis();
            SyntaxAnalysis syntax = new SyntaxAnalysis();
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            CodeGeneratorIR cgM = new CodeGeneratorIR();
            
            parser p = new parser(new Lexer(new FileReader(srcInputFilename), sf), sf);
            ProgramNode result = (ProgramNode) p.parse().value;

            System.out.println(result.toString());

            result.accept(semantic);
            result.accept(syntax);

            ErrorHandler errors = ErrorHandler.getInstance();

            errors.printError();
            
            if(errors.hasError()){
                System.exit(1);
            }

            if(astOutFilename != null) {
                PrintStream ps = new PrintStream( new FileOutputStream( new File(astOutFilename)));
                ps.print(syntax.toString());
                ps.flush();
                ps.close();
            }
            
            result.genCode(cgM);
            if(irOutFilename != null){
                PrintStream ps = new PrintStream( new FileOutputStream( new File(irOutFilename)));
                ps.print(cgM.toString());
                ps.flush();
                ps.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}

    //TODO if else grammar conflict
    //TODO assign grammar conflict


    //TODO semantic float limits
    //TODO warning l - r value