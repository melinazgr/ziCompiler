package Nodes;

public class NumberNode extends ExpressionNode{
    public String value;

    public NumberNode (String value) {
        this.value = value;
    }

    public String toString () {
        return value; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}