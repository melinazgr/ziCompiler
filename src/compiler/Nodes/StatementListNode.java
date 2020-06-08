package Nodes;

import java.util.*;

public class StatementListNode extends StatementNode{
    ArrayList <StatementNode> statements = new ArrayList <StatementNode>();

    public String toString () {
        
        StringBuilder s = new StringBuilder(); 

        for (StatementNode statement : statements) { 
            if(statement != null ){
                s.append(statement.toString());
                s.append("\n");
            }

            else {
                s.append("null stmt\n");
            }
        
        }



        return s.toString();
    }

    public void addStatement(StatementNode stmt) {
        statements.add(stmt);
    }
}