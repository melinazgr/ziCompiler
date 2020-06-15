package Nodes;

import Model.*;

public class IfStmtNode extends StatementNode{
    public BoolExpressionNode boolExpr;
    public StatementNode  stmt1, stmt2;

    public IfStmtNode (BoolExpressionNode boolExpr, StatementNode stmt1) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
    }

    public IfStmtNode (BoolExpressionNode boolExpr, StatementNode stmt1, StatementNode stmt2) {
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
    
    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){
        
        if(stmt2 == null){
            boolExpr.jump(cg, false, after);
        
            int label1 = newLabel();

            cg.emitLabel(label1);
            stmt1.genCode(cg, label1, after, StatementTypeGeneration.ALL);
        }
        else{
            int label1 = newLabel();
            int label2 = newLabel();
            boolExpr.jump(cg, false, label2);

            cg.emitLabel(label1);
            stmt1.genCode(cg, label1, after,  StatementTypeGeneration.ALL);
            cg.emitLabel(label2);

            stmt2.genCode(cg, label2, after,  StatementTypeGeneration.ALL);
        }
    } 
}