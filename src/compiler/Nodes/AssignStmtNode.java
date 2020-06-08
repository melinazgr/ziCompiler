package Nodes;

public class AssignStmtNode extends StatementNode{
    ExpressionNode expr;

    public AssignStmtNode (ExpressionNode expr){
        this.expr = expr;
    }

    public String toString () {
        return expr.toString() + ';'; 
    }
    
}