package Error;

public class Error {
    String message, errorId, id;
    int column, line;
    boolean isError;

    public Error (String message, String id, int column, int line){
        this.message = message;
        this.column = column;
        this.line = line;  
        this.id = id;
    }

    public String toString(){
        return  String.format("Error: %s at line %d, column %d.", this.message, this.line, this.column);
    }
}