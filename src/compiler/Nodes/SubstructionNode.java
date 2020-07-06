package Nodes;

import Model.*;

/**
 * Substruction Node Class
 * 
 * @author Melina Zikou
 */
public class SubstructionNode extends ExpressionNode {
    public ExpressionNode rval, term;

    /**
     * Constructor which creates the substruction node
     * @param rval right value of substruction
     * @param term the other term of the substruction
     */
    public SubstructionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    /**
     * Function that converts substruction to string
     * @return substruction stirng
     */
    public String toString () {

        String s =  rval.toString() + " - " 
                    + term.toString();
        return s; 
    }

    /**
     * the right and left value of substruction to something simpler
     * @param cg CodeGenerator object, the structure where code is saved
     * @return temporary variable 
     */
    public ExpressionNode reduce(CodeGenerator cg){
        SubstructionNode reducedAddNode =  new SubstructionNode(this.rval.reduce(cg), this.term.reduce(cg));
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        ExpressionNode exprRval = reducedAddNode.rval, exprTerm = reducedAddNode.term;

        if(this.type == VariableType.FLOAT && reducedAddNode.term.type == VariableType.INT){
            TempExprNode tempTerm = scope.getTemp(this.type);
            cg.emitCast( reducedAddNode.term, tempTerm);
            exprTerm = tempTerm;
        }
        if(this.type == VariableType.FLOAT && reducedAddNode.rval.type == VariableType.INT){
            TempExprNode tempRval = scope.getTemp(this.type);
            cg.emitCast( reducedAddNode.rval, tempRval);
            exprRval = tempRval;
        }

        cg.emitStatement("-", exprRval, exprTerm, temp);

        if(reducedAddNode.rval instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.rval);
        }
        if(reducedAddNode.term instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.term);
        }
        
        
        return temp;
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
}