package Nodes;

public class NegateNode extends ExpressionNode{
    public ExpressionNode factor;

    public NegateNode (ExpressionNode  factor) {
        this.factor = factor; 
    }

    public String toString () {
        return factor.toString(); 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}