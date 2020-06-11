package Nodes;

import Model.*;

public class AssignStmtNode extends StatementNode{
    public ExpressionNode expr;
    public SymbolTable table;

    public AssignStmtNode (ExpressionNode expr){
        this.expr = expr;
    }

    public String toString () {
        return expr.toString() + ';'; 
    }

    public void accept(Visitor v){
        v.visit(this);
    }
}