package Error;

/**
 * Error Class / error identity
 * 
 * @author Melina Zikou
 */
public class Error {
    
    String message, errorId, id;
    int column, line;
    boolean isError;

    /**
     * constructor of error
     * @param message of the error
     * @param id name of the variable
     * @param column the error appears in source code
     * @param line the error appears in source code
     */
    public Error (String message, String id, int column, int line){
        this.message = message;
        this.column = column;
        this.line = line;  
        this.id = id;
    }

    /**
     * print error
     * @return string with error
     */
    public String toString(){
        return  String.format("Error: %s at line %d, column %d.", this.message, this.line, this.column);
    }
}