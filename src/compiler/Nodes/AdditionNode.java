package Nodes;

public class AdditionNode extends ExpressionNode{
    ExpressionNode rval, term;

    public AdditionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    public String toString () {

        String s =  rval.toString() + " + " 
                    + term.toString();

        return s; 
    }
    
}