package Nodes;

import Model.*;

/**
 * For Statement Node Class
 * 
 * @author Melina Zikou
 */
public class ForStmtNode extends StatementNode{
   
    public AssignExpressionNode opAssignExpr1, opAssignExpr2;
    public BoolExpressionNode opBoolExpr;
    public StatementNode stmt;

    /**
     * constructor that creates for statement node
     * @param opAssignExpr1 first expression of for statement
     * @param opBoolExpr second
     * @param opAssignExpr2 third
     * @param stmt the statement after the condition of for statement
     */
    public ForStmtNode (AssignExpressionNode opAssignExpr1, BoolExpressionNode opBoolExpr, AssignExpressionNode opAssignExpr2, StatementNode stmt){
        this.opAssignExpr1 = opAssignExpr1;
        this.opAssignExpr2 = opAssignExpr2;
        this.opBoolExpr = opBoolExpr;
        this.stmt = stmt;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {

        StringBuilder s = new StringBuilder(); 
        
        s.append("for (" + opAssignExpr1.toString() + "; " + opBoolExpr.toString() + "; " + opAssignExpr1.toString() + " ) {\n");
        s.append(stmt.toString());
        s.append(NodeFormatter.getInstance().getSpaces());
        s.append("} \n");

        return s.toString();
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * generates code
     * @param cg where the code is saved
     * @param begin starting label 
     * @param after ending label
     * @param stmtGenType type of statement
     */
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