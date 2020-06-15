package Nodes;

import Model.*;

public class AssignExpressionNode extends ExpressionNode{
    public ExpressionNode expr;
    public IdNode id;
    public AssignExpressionNode (IdNode id, ExpressionNode expr) {
        this.expr = expr;
        this.id = id;
    }

    public String toString () {

        String s =  id + " = " + expr.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
    
    public ExpressionNode reduce(CodeGenerator cg){
        ExpressionNode expression = expr.reduce(cg);
        cg.emitStatement("=", expression, null, this.id);

        return this.id;
    }

    public void genCode(CodeGenerator cg) {
        ExpressionNode expression = this.expr.reduce(cg);

        cg.emitStatement("=", expression, null, this.id);
    } 
}