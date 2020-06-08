package Nodes;

public class IfStmtNode extends StatementNode{
    ExpressionNode boolExpr;
    StatementNode stmt;

    public IfStmtNode (ExpressionNode boolExpr, StatementNode stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }

    public String toString () {
        String s = "if (" +   boolExpr.toString() + ") {\n" +
                    stmt.toString() + "\n}";
        return s; 
    }
}