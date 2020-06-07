package Nodes;

import java.util.*;

public class StatementListNode extends StatementNode{
    ArrayList <StatementNode> statements = new ArrayList <StatementNode>();

    public String toString () {
        return " ";
    }

    public void addStatement(StatementNode stmt) {
        statements.add(stmt);
    }
}