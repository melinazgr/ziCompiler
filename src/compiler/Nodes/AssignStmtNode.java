package Nodes;

public class AssignStmtNode extends StatementNode{
    public ExpressionNode expr;

    public AssignStmtNode (ExpressionNode expr){
        this.expr = expr;
    }

    public String toString () {
        return expr.toString() + ';'; 
    }

    public void accept(Visitor v){
        v.visit(this);
    }
    
}