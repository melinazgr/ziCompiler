package Nodes;

import Model.*;

/**
 * Multiplication Node Class
 * 
 * @author Melina Zikou
 */
public class MultiNode extends ExpressionNode{
   
    public ExpressionNode factor, term;

    /**
     * Constructor which creates the multiplication node
     * @param factor one of the terms of division
     * @param term one of the terms of the division
     */
    public MultiNode (ExpressionNode term, ExpressionNode  factor) {
        this.factor = factor;
        this.term = term;
    }

    /**
     * Function that converts multiplication to string
     * @return multiplication stirng
     */
    public String toString () {

        String s =  term.toString() + " * " 
                    + factor.toString();

        return s; 
    }

    /**
     * the right and left value of multiplication to something simpler
     * @param cg CodeGenerator object, the structure where code is saved
     * @return temporary variable 
     */
    public ExpressionNode reduce(CodeGenerator cg){
        MultiNode reducedAddNode =  new MultiNode(this.term.reduce(cg), this.factor.reduce(cg));
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        ExpressionNode exprFactor = reducedAddNode.factor, exprTerm = reducedAddNode.term;
        
        if(this.type == VariableType.FLOAT && reducedAddNode.term.type == VariableType.INT){
            TempExprNode tempTerm = scope.getTemp(this.type);
            cg.emitCast( reducedAddNode.term, tempTerm);
            exprTerm = tempTerm;
        }
        if(this.type == VariableType.FLOAT && reducedAddNode.factor.type == VariableType.INT){
            TempExprNode tempFactor = scope.getTemp(this.type);
            cg.emitCast( reducedAddNode.factor, tempFactor);
            exprFactor = tempFactor;
        }

        cg.emitStatement("*", exprTerm, exprFactor, temp);

        if(reducedAddNode.factor instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.factor);
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