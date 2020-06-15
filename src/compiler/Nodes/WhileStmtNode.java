package Nodes; 

import Model.*;

public class WhileStmtNode extends StatementNode{
    
    public BoolExpressionNode boolExpr;
    public StatementNode stmt;

    public WhileStmtNode (BoolExpressionNode boolExpr, StatementNode stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }

    public String toString () {
        StringBuilder s = new StringBuilder(); 
        
        s.append("while (" + boolExpr.toString() + ") {\n");
        s.append(stmt.toString());
        s.append(NodeFormatter.getInstance().getSpaces());
        s.append("} \n");

        return s.toString();
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){
        this.after = after;

        stmt.genCode(cg, 0, 0, StatementTypeGeneration.DECL_ONLY);

        int beforeCondition = newLabel();

        cg.emitLabel(beforeCondition);

        boolExpr.jump(cg, false, after);

        stmt.genCode(cg, 0, 0, StatementTypeGeneration.DECL_SKIP);
        cg.emitJump(beforeCondition);
    } 
}