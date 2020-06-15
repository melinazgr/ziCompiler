package Nodes;

import Model.*;

public class AssignStmtNode extends StatementNode{
    public AssignExpressionNode expr;
    public SymbolTable table;

    public AssignStmtNode (AssignExpressionNode expr){
        this.expr = expr;
    }

    public String toString () {
        return expr.toString() + ';'; 
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType) {
        this.expr.reduce(cg);
    } 
}