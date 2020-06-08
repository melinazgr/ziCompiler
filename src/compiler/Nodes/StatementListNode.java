package Nodes;

import java.util.*;

public class StatementListNode extends StatementNode{
    ArrayList <StatementNode> statements = new ArrayList <StatementNode>();
    static int depth = 0;

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
}