package Nodes;

import Model.*;

public class AdditionNode extends ExpressionNode{
    public ExpressionNode rval, term;

    public AdditionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    public String toString () {

        String s =  rval.toString() + " + " 
                    + term.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public ExpressionNode reduce(CodeGenerator cg){
        AdditionNode reducedAddNode =  new AdditionNode(this.rval.reduce(cg), this.term.reduce(cg));
        TempExprNode temp = new TempExprNode(this.type);
        
        cg.emitStatement("+", reducedAddNode.rval, reducedAddNode.term, temp);
        return temp;
    }
}