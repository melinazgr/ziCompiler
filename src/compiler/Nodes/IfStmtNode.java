package Nodes;

public class IfStmtNode extends StatementNode{
    public ExpressionNode boolExpr;
    public StatementNode  stmt1, stmt2;

    public IfStmtNode (ExpressionNode boolExpr, StatementNode stmt1) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
    }

    public IfStmtNode (ExpressionNode boolExpr, StatementNode stmt1, StatementNode stmt2) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
    
    public String toString () {
        StringBuilder s = new StringBuilder(); 
        
        s.append("if (" +   boolExpr.toString() + ") {\n");
        s.append(stmt1.toString());
        s.append(NodeFormatter.getInstance().getSpaces());
        s.append("} \n");
    
        if(stmt2 != null){
            s.append(NodeFormatter.getInstance().getSpaces());
            s.append("else { \n");
            s.append(stmt2.toString());
            s.append(NodeFormatter.getInstance().getSpaces());
            s.append("} \n");
        }
        
        return s.toString();
    }

    public void accept(Visitor v){
        v.visit(this);
    }
    
}

