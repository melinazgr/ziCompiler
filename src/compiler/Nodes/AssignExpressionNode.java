package Nodes;

public class AssignExpressionNode extends ExpressionNode{
    ExpressionNode expr;
    String id;
    public AssignExpressionNode (String id, ExpressionNode expr) {
        this.expr = expr;
        this.id = id;
    }

    public String toString () {

        String s =  id + " = "
                    + expr.toString();

        return s; 
    }
    
}