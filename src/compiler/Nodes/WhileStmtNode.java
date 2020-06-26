package Nodes; 

import Model.*;

/**
 * While Statement Node Class
 * 
 * @author Melina Zikou
 */
public class WhileStmtNode extends StatementNode{
    
    public BoolExpressionNode boolExpr;
    public StatementNode stmt;

    /**
     * constructor that creates while statement node
     * @param boolExpr the condition
     * @param stmt the statement
     */
    public WhileStmtNode (BoolExpressionNode boolExpr, StatementNode stmt) {
        this.boolExpr = boolExpr;
        this.stmt = stmt;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        StringBuilder s = new StringBuilder(); 
        
        s.append("while (" + boolExpr.toString() + ") {\n");
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
        this.after = after;

        stmt.genCode(cg, 0, 0, StatementTypeGeneration.DECL_ONLY);

        int beforeCondition = newLabel();

        cg.emitLabel(beforeCondition);

        boolExpr.jump(cg, false, after);

        stmt.genCode(cg, 0, 0, StatementTypeGeneration.DECL_SKIP);
        cg.emitJump(beforeCondition);
    } 
}