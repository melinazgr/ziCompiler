package Nodes;

import Model.*;

/**
 * Negate Node Class
 * 
 * @author Melina Zikou
 */
public class NegateNode extends ExpressionNode{
    
    public ExpressionNode factor;
    
    /**
     * constructor that creates negation node
     * @param factor the factor to be negated
     */
    public NegateNode (ExpressionNode  factor) {
        this.factor = factor; 
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        return "-" + factor.toString(); 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * simplifies the content of negation
     * @param cg CodeGenerator object, the structure where code is saved
     * @return temporary variable 
     */
    public ExpressionNode reduce(CodeGenerator cg){
        NegateNode reducedAddNode =  new NegateNode(this.factor.reduce(cg));
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        
        cg.emitStatement("-", reducedAddNode.factor, null, temp);

        if(reducedAddNode.factor instanceof TempExprNode){
            scope.returnTemp((TempExprNode)reducedAddNode.factor);
        }
        
        return temp;
    }
}