package Nodes;

public class IfElseStmtNode extends StatementNode{
    ExpressionNode boolExpr;
    StatementNode stmt1, stmt2;

    public IfElseStmtNode (ExpressionNode boolExpr, StatementNode stmt1, StatementNode stmt2) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
}