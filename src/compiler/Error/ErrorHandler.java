package Error;

import java.util.*;

/**
 * Class that handles errors during compilation
 * of the source program
 * 
 * @author Melina Zikou
 */
public class ErrorHandler {
    //class created as singleton
    private static ErrorHandler singleInstance = null;
    
    ArrayList<Error> errors = new ArrayList<Error>();

    private ErrorHandler () {

    }

    public static ErrorHandler getInstance(){
        if(singleInstance == null){
            singleInstance = new ErrorHandler();
        }

        return singleInstance;
    }

    /**
     * create new error
     * @param message to be printed
     * @param id name of the variable
     * @param column it appears in the source code
     * @param line it appears in the source code
     */
    public void addError(String message, String id, int column, int line){
        Error e = new Error(message, id, column, line);
        this.errors.add(e);
    }

    /**
     * print error
     */
    public void printError(){
        for(Error e: errors){
            System.out.println(e.toString());
        }
    }

    /**
     * check if there are any errors
     * @return true if there are errors
     *         false otherwise
     */
    public boolean hasError(){
        return !errors.isEmpty();
    }
}