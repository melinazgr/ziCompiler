package Nodes;

public class AssignExpressionNode extends ExpressionNode{
    public ExpressionNode expr;
    public IdNode id;
    public AssignExpressionNode (IdNode id, ExpressionNode expr) {
        this.expr = expr;
        this.id = id;
    }

    public String toString () {

        String s =  id + " = "
                    + expr.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
    
}