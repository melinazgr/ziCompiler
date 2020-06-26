package Nodes;

import Model.*;

/**
 * Program Node Class
 * 
 * the beginning of each program
 * 
 * @author Melina Zikou
 */
public class ProgramNode extends Node{
    
    public String id;
    public StatementListNode compStmt;

    /**
     * Constructor that creates the program node
     * @param id name of the program (eg. Fibonacci)
     * @param compStmt the following statements of the program
     */
    public ProgramNode (String id, StatementListNode compStmt) {
        this.id = id;
        this.compStmt = compStmt;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        
        StringBuilder s = new StringBuilder();
        
        s.append("mainclass " + id + " { \n");
        NodeFormatter.getInstance().incDepth();
        s.append(NodeFormatter.getInstance().getSpaces());
        s.append("public static void main () {\n");

        s.append(compStmt.toString());
        s.append(NodeFormatter.getInstance().getSpaces());

        s.append("}\n");
        NodeFormatter.getInstance().decDepth();
        s.append(NodeFormatter.getInstance().getSpaces());

        s.append("}\n");
        NodeFormatter.getInstance().decDepth();
        
        return s.toString();
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * generates code
     * @param cg where the code is formatted and saved
     */
    public void genCode (CodeGenerator cg){
        int label1 = newLabel();
        int label2 = newLabel();

        cg.emitLabel(label1);
        compStmt.genCode(cg, label1, label2, StatementTypeGeneration.ALL);
        cg.emitLabel(label2);
    }
}