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
}