package Nodes;

public class NullStmtNode extends StatementNode{
    public String toString () {
        return ";"; 
    }

    public void accept(Visitor v){
        v.visit(this);
    }
    
}