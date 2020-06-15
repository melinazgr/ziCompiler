package Nodes;

import Model.*;

public class BoolExpressionNode extends ExpressionNode{
    public ExpressionNode expr1, expr2;
    public Operator operator;
    public BoolExpressionNode (ExpressionNode expr1, Operator operator, ExpressionNode expr2) {
        this.operator = operator;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public String toString () {

        String s =  expr1.toString() + " "
                    + FormatHelper.getOpName(operator) + " "
                    + expr2.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public void jump(CodeGenerator cg, boolean condition, int label){
        ExpressionNode a = expr1.reduce(cg);
        ExpressionNode b = expr2.reduce(cg);

        cg.emitJump(condition, this.operator.toString(), a, b, label);
    }
}
