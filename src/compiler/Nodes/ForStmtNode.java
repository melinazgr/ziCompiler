package Nodes;

public class ForStmtNode extends StatementNode{
    public AssignExpressionNode opAssignExpr1, opAssignExpr2;
    public ExpressionNode opBoolExpr;
    public StatementNode stmt;

    public ForStmtNode (AssignExpressionNode opAssignExpr1, ExpressionNode opBoolExpr, AssignExpressionNode opAssignExpr2, StatementNode stmt){
        this.opAssignExpr1 = opAssignExpr1;
        this.opAssignExpr2 = opAssignExpr2;
        this.opBoolExpr = opBoolExpr;
        this.stmt = stmt;
    }

    public String toString () {
        StringBuilder s = new StringBuilder(); 
        
        s.append("for (" + opAssignExpr1.toString() + "; " + opBoolExpr.toString() + "; " + opAssignExpr1.toString() + " ) {\n");
        s.append(stmt.toString());
        s.append(NodeFormatter.getInstance().getSpaces());
        s.append("} \n");

        return s.toString();
    }

    public void accept(Visitor v){
        v.visit(this);
    }
}


    