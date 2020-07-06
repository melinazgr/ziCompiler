package Main;

import Nodes.*;
import Model.*;
import Error.*;
import java.io.*;
import java_cup.runtime.*;

/**
 * 
 * 
 * 
 * @author Melina Zikou
 * 
 * 
 *
 */
public class Main {

    public static boolean Debug = false;
    public static void main(String[] argv)
    {

        
        String helpText = "Usage: java Main [-debug]\n"+
                          "                 [-v]\n"+
                          "                 [-ast <ast filename>]\n" +
                          "                 [-ir <filename>]\n"+
                          "                 [-out <mixal out filename>]\n" + 
                          "                 -i <src filename> "; 
        String astOutFilename = null;
        String srcInputFilename = null;
        String irOutFilename = null;
        String mixalOutFilename = null;
        boolean verbose = false;


        /* command line / batch file arguments */
        try 
        {
            for ( int i = 0; i < argv.length; i++ ) {
				if ( argv[ i ].equals( "-debug" ) ) {
					Main.Debug = true;
				}
                else if ( argv[ i ].equals( "-i" ) ) {
					i++;
                    if ( i >= argv.length ){
                        throw new Exception( "Missing input filename" );
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
                        throw new Exception( "Missing ir filename" );
                    }

					irOutFilename = argv[ i ];
                }

                else if ( argv[ i ].equals( "-out" ) ) {
					i++;
                    if ( i >= argv.length ){
                        throw new Exception( "Missing out filename" );
                    }

					mixalOutFilename = argv[ i ];
                }
                else if ( argv[ i ].equals( "-v" ) ) {
					verbose = true;
                }
				else {
					throw new Exception(helpText);
                }
            }

            if (srcInputFilename == null){
                throw new Exception(helpText);
            }
            
            if(mixalOutFilename == null){
                mixalOutFilename = srcInputFilename + ".mixal";
            }

            System.out.println("Compiling program: " + srcInputFilename);
            System.out.println("Generating output MIXAL program: " + mixalOutFilename);


            SemanticAnalysis semantic = new SemanticAnalysis();
            SyntaxAnalysis syntax = new SyntaxAnalysis();
            
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            
            CodeGeneratorIR cgIR = new CodeGeneratorIR();
            CodeGeneratorMIXAL cgM = new CodeGeneratorMIXAL();
            
            parser p = new parser(new Lexer(new FileReader(srcInputFilename), sf), sf);
            ProgramNode result = (ProgramNode) p.parse().value;

            if(verbose){
                System.out.println(result.toString());
            }

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
            
            result.genCode(cgIR);
            cgIR.optimize();

            if(irOutFilename != null){
                PrintStream ps = new PrintStream( new FileOutputStream( new File(irOutFilename)));
                ps.print(cgIR.toString());
                ps.flush();
                ps.close();
            }

            PrintStream ps = new PrintStream( new FileOutputStream( new File(mixalOutFilename)));
            cgM.genCode(cgIR);

            ps.print(cgM.toString());
            ps.flush();
            ps.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(100);
        }
    }
}

    //TODO if else grammar conflict
    //TODO assign grammar conflict

    //TODO semantic float limits
    //TODO warning l - r value

    //TODO decltype comments