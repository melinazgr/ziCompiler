package Nodes; 

public class WhileStmtNode extends StatementNode{
    ExpressionNode boolExpr;
    StatementNode stmt;

    public WhileStmtNode (ExpressionNode boolExpr, StatementNode stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }

    public String toString () {

        String s = "while (" + boolExpr.toString() + ") {\n" 
                    + stmt.toString()
                    + "} \n"  ;

        return s; 
    }
}