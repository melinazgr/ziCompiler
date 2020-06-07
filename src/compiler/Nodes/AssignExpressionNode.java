package Nodes;

public class AssignExpressionNode extends ExpressionNode{
    ExpressionNode expr;
    Operator operator;
    String id;
    public AssignExpressionNode (String id, Operator operator, ExpressionNode expr) {
        this.operator = operator;
        this.expr = expr;
        this.id = id;
    }
    
}