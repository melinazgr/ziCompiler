package Nodes;

public class ProgramNode extends Node{
    
    String id;
    StatementNode compStmt;

    public ProgramNode (String id, StatementNode compStmt) {
        this.id = id;
        this.compStmt = compStmt;
    }

    public String toString () {
        String s =  "mainclass " + id + " { \n"
                    + "public static void main () {\n"
                    + compStmt.toString() 
                    +  "} \n} \n";
        return s;
    }
}