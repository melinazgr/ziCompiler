package Nodes;

import Model.*;

/**
 * Function Call Node Class
 * 
 * @author Melina Zikou
 */
public class FunctionCallNode extends StatementNode{
    
    public String id;
    public ExpressionNode argument;
    
    public FunctionCallNode (String id, ExpressionNode argument){
        this.id = id;
        this.argument = argument;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        return id + " (" + argument.toString() + ");"; 
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
        
        ExpressionNode temp = this.argument.reduce(cg);
        cg.emitFunctionCall(id, temp);   
    }
}