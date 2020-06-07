package Nodes;

public class NegateNode extends ExpressionNode{
    ExpressionNode factor;

    public NegateNode (ExpressionNode  factor) {
        this.factor = factor;
    }
}