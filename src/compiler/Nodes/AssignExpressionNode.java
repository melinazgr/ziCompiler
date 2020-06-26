package Nodes;

import Model.*;

/**
 * Assign Expression Node Class
 * 
 * @author Melina Zikou
 */
public class AssignExpressionNode extends ExpressionNode{
    
    public ExpressionNode expr;
    public IdNode id;
    
    /**
     * Constructor
     * @param id idNode with the identity of a variable
     * @param expr the expression of the assign expression
     */
    public AssignExpressionNode (IdNode id, ExpressionNode expr) {
        this.expr = expr;
        this.id = id;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {

        String s =  id + " = " + expr.toString();

        return s; 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
    
    /**
     * reduce code to generate code
     * @param cg CodeGenerator object, the structure where code generated is saved
     * @return the expression 
     */
    public ExpressionNode reduce(CodeGenerator cg){
        ExpressionNode expression = expr.reduce(cg);
        cg.emitStatement("=", expression, null, this.id);
        
        if(expression instanceof TempExprNode){
            StatementListNode scope = (StatementListNode) this.getScopeParent();
            scope.returnTemp((TempExprNode) expression);
        }
        
        return this.id;
    }

    /**
     * generate code 
     * @param cg CodeGenerator object, the structure where code generated is saved
     */
    public void genCode(CodeGenerator cg) {
        ExpressionNode expression = this.expr.reduce(cg);

        cg.emitStatement("=", expression, null, this.id);
        if(expression instanceof TempExprNode){
            StatementListNode scope = (StatementListNode) this.getScopeParent();
            scope.returnTemp((TempExprNode) expression);
        }
    } 
}