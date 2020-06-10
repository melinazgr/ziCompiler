package Error;

import java.util.*;

public class ErrorHandler {
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

    public void addError(String message, String id, int column, int line){
        Error e = new Error(message, id, column, line);
        this.errors.add(e);
    }

    public void printError(){
        for(Error e: errors){
            System.out.println(e.toString());
        }
    }
}