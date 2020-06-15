package Nodes;

import Model.*;

public class ForStmtNode extends StatementNode{
    public AssignExpressionNode opAssignExpr1, opAssignExpr2;
    public BoolExpressionNode opBoolExpr;
    public StatementNode stmt;

    public ForStmtNode (AssignExpressionNode opAssignExpr1, BoolExpressionNode opBoolExpr, AssignExpressionNode opAssignExpr2, StatementNode stmt){
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

    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){
        
        int label1 = newLabel();
        int label2 = newLabel();

        stmt.genCode(cg, label2, after, StatementTypeGeneration.DECL_ONLY);

        opAssignExpr1.genCode(cg);

        this.after = after;
        cg.emitLabel(label1);

        opBoolExpr.jump(cg, false, after);
        
        cg.emitLabel(label2);
        stmt.genCode(cg, label2, after, StatementTypeGeneration.DECL_SKIP);

        opAssignExpr2.genCode(cg);

        cg.emitJump(label1);
    } 
}


    