package Nodes;

public class SubstructionNode extends ExpressionNode {
    public ExpressionNode rval, term;

    public SubstructionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    public String toString () {

        String s =  rval.toString() + " - " 
                    + term.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}