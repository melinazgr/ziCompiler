package Nodes;

import Model.*;

public class MultiNode extends ExpressionNode{
    public ExpressionNode factor, term;

    public MultiNode (ExpressionNode term, ExpressionNode  factor) {
        this.factor = factor;
        this.term = term;
    }

    public String toString () {

        String s =  term.toString() + " * " 
                    + factor.toString();

        return s; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public ExpressionNode reduce(CodeGenerator cg){
        MultiNode reducedAddNode =  new MultiNode(this.term.reduce(cg), this.factor.reduce(cg));
        TempExprNode temp = new TempExprNode(this.type);
        
        cg.emitStatement("*", reducedAddNode.term, reducedAddNode.factor, temp);
        return temp;
    }

    public ExpressionNode genTerm(CodeGenerator cg){
        return new MultiNode(this.term.reduce(cg), this.factor.reduce(cg));
    } 
}