package Nodes;

import Model.*;
import java.util.*;

public class StatementListNode extends StatementNode{
    
    public ArrayList <StatementNode> statements = new ArrayList <StatementNode>();
    static int depth = 0;
    private SymbolTable table;

    public String toString () {
        
        StringBuilder s = new StringBuilder(); 

        NodeFormatter.getInstance().incDepth();
        
        for (StatementNode statement : statements) { 
            s.append(NodeFormatter.getInstance().getSpaces());
            s.append(statement.toString());
            s.append("\n");
        }

        NodeFormatter.getInstance().decDepth();

        return s.toString();
    }

    public void addStatement(StatementNode stmt) {
        statements.add(stmt);
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    public void setSymbolTable(SymbolTable table){
        this.table = table;
    }

    public SymbolTable getSymbolTable(){
        return this.table;
    }

    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType) {
        
        for (StatementNode statement : statements) { 
            if(statement instanceof DeclarationStmtNode && stmtGenType == StatementTypeGeneration.DECL_SKIP){
                continue;
            }

            if(!(statement instanceof DeclarationStmtNode) && stmtGenType == StatementTypeGeneration.DECL_ONLY){    
                continue;
            }

            int label1 = newLabel();
            int label2 = newLabel();

            cg.emitLabel(label1);
            statement.genCode(cg, label1, label2, stmtGenType);
            cg.emitLabel(label2);      
        }
    } 
}