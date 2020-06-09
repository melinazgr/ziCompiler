package Nodes;

public class DivisionNode extends ExpressionNode {
    public ExpressionNode factor, term;

    public DivisionNode (ExpressionNode term, ExpressionNode  factor) {
        this.factor = factor;
        this.term = term;
    }

    public String toString () {

        String s =  term.toString() + " / " 
                    + factor.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}