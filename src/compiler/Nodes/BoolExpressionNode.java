package Nodes;

import Model.*;

/**
 * Bool Expression Node Class
 * 
 * @author Melina Zikou
 */
public class BoolExpressionNode extends ExpressionNode{
    
    public ExpressionNode expr1, expr2;
    public Operator operator;

    /**
     * Constructor that creates bool expression node
     * @param expr1 first expression
     * @param operator the operator of the expression
     * @param expr2 second expression
     */
    public BoolExpressionNode (ExpressionNode expr1, Operator operator, ExpressionNode expr2) {
        this.operator = operator;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {

        String s =  expr1.toString() + " "
                    + FormatHelper.getOpName(operator) + " "
                    + expr2.toString();

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
     * jump operation for code generation
     * @param cg where the code generated is saved
     * @param condition iffalse / iftrue
     * @param label the label of the expression
     */
    public void jump(CodeGenerator cg, boolean condition, int label){
        ExpressionNode a = expr1.reduce(cg);
        ExpressionNode b = expr2.reduce(cg);

        cg.emitJump(condition, this.operator.toString(), a, b, label);
    }
}