package Nodes;

public class DivisionNode extends ExpressionNode {
    ExpressionNode factor, term;

    public DivisionNode (ExpressionNode term, ExpressionNode  factor) {
        this.factor = factor;
        this.term = term;
    }
}