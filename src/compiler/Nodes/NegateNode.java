package Nodes;

import Model.*;

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

    public ExpressionNode reduce(CodeGenerator cg){
        NegateNode reducedAddNode =  new NegateNode(this.factor.reduce(cg));
        TempExprNode temp = new TempExprNode(this.type);
        
        cg.emitStatement("-", reducedAddNode.factor, null, temp);
        return temp;
    }

    public ExpressionNode genTerm(CodeGenerator cg){
        return new NegateNode(this.factor.reduce(cg));
    }
}