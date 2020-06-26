package Nodes;

import Model.*;

/**
 * Assign Statement Node Class
 * 
 * @author Melina Zikou
 */
public class AssignStmtNode extends StatementNode{
    
    public AssignExpressionNode expr;
    public SymbolTable table;

    /**
     * constructor that creates assign expression node
     * @param expr the expression 
     */
    public AssignStmtNode (AssignExpressionNode expr){
        this.expr = expr;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        return expr.toString() + ';'; 
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
    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType) {
        this.expr.reduce(cg);
    } 
}