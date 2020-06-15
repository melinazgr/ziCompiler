package Nodes;

import Model.*;

public class NullStmtNode extends StatementNode{
    public String toString () {
        return ";"; 
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){

    }

    
}