package Nodes;

import Model.*;

/**
 * If Statement Node Class
 * 
 * @author Melina Zikou
 */
public class IfStmtNode extends StatementNode{
    
    public BoolExpressionNode boolExpr;
    public StatementNode  stmt1, stmt2;

    /**
     * constructor that creates if statement node
     * @param boolExpr the condition of if statement
     * @param stmt1 the statement after if condition
     */
    public IfStmtNode (BoolExpressionNode boolExpr, StatementNode stmt1) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
    }

    /**
     * constructor that creates if statement with else
     * @param boolExpr the condition of if statement
     * @param stmt1 the statement after if condition
     * @param stmt2 the statement after else
     */
    public IfStmtNode (BoolExpressionNode boolExpr, StatementNode stmt1, StatementNode stmt2) {
        this.boolExpr = boolExpr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
    }
    
    /**
     * Convert to string
     * @return string
     */
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
            cg.emitJump(after);
            cg.emitLabel(label2);

            stmt2.genCode(cg, label2, after,  StatementTypeGeneration.ALL);
        }
    } 
}