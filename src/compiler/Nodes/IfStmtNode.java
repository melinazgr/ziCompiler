package Nodes;

public class IfStmtNode extends StatementNode{
    ExpressionNode boolExpr;
    StatementNode stmt;

    public IfStmtNode (ExpressionNode boolExpr, StatementNode stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }
}