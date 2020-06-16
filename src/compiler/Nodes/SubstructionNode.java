package Nodes;

import Model.*;

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

    public ExpressionNode reduce(CodeGenerator cg){
        SubstructionNode reducedAddNode =  new SubstructionNode(this.rval.reduce(cg), this.term.reduce(cg));
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        
        cg.emitStatement("-", reducedAddNode.rval, reducedAddNode.term, temp);

        if(reducedAddNode.rval instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.rval);
        }
        if(reducedAddNode.term instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.term);
        }
        
        
        return temp;
    }

    public ExpressionNode genTerm(CodeGenerator cg){
        return new SubstructionNode(this.rval.reduce(cg), this.term.reduce(cg));
    }
}