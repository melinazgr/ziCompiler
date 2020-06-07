package Nodes;

public class ForStmtNode extends StatementNode{
    ExpressionNode opAssignExpr1, opBoolExpr, opAssignExpr2;
    StatementNode stmt;

    public ForStmtNode (ExpressionNode opAssignExpr1, ExpressionNode opBoolExpr, ExpressionNode opAssignExpr2, StatementNode stmt){
        this.opAssignExpr1 = opAssignExpr1;
        this.opAssignExpr2 = opAssignExpr2;
        this.opBoolExpr = opBoolExpr;
        this.stmt = stmt;
    }
}