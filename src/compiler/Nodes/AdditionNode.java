package Nodes;

import Model.*;

/**
 * Addition Node Class
 * 
 * @author Melina Zikou
 */
public class AdditionNode extends ExpressionNode{
    
    public ExpressionNode rval, term;

    /**
     * Constructor which creates the addition node
     * @param rval right value of addition
     * @param term the other term of the addition
     */
    public AdditionNode (ExpressionNode rval, ExpressionNode term) {
        this.rval = rval;
        this.term = term;
    }

    /**
     * Function that converts addition to string
     * @return addition stirng
     */
    public String toString () {

        String s =  rval.toString() + " + " 
                    + term.toString();

        return s; 
    }

    /**
     * the right and left value of addition to something simpler
     * @param cg CodeGenerator object, the structure where code is saved
     * @return temporary variable 
     */
    public ExpressionNode reduce(CodeGenerator cg){
        AdditionNode reducedAddNode =  new AdditionNode(this.rval.reduce(cg), this.term.reduce(cg));
        StatementListNode scope = (StatementListNode) this.getScopeParent();
        TempExprNode temp = scope.getTemp(this.type);
        
        cg.emitStatement("+", reducedAddNode.rval, reducedAddNode.term, temp);
        
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