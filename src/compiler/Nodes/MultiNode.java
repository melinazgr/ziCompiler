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
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        
        cg.emitStatement("*", reducedAddNode.term, reducedAddNode.factor, temp);

        if(reducedAddNode.factor instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.factor);
        }
        if(reducedAddNode.term instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.term);
        }
        
        return temp;
    }

    public ExpressionNode genTerm(CodeGenerator cg){
        return new MultiNode(this.term.reduce(cg), this.factor.reduce(cg));
    } 
}