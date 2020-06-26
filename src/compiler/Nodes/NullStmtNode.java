package Nodes;

import Model.*;

/**
 * Null Statement Node 
 * 
 * @author Melina Zikou
 */
public class NullStmtNode extends StatementNode{
    
    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        return ";"; 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * generates code
     * @param cg where the code is saved
     * @param begin starting label 
     * @param after ending label
     * @param stmtGenType type of statement
     */
    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){

    }
}