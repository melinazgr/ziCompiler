package Nodes;

public class SubstructionNode extends ExpressionNode {
    ExpressionNode rval, term;

    public SubstructionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    public String toString () {

        String s =  rval.toString() + " - " 
                    + term.toString();

        return s; 
    }
}