package Nodes;

import Main.*;

/**
 * Temporary Expression Node Class
 * 
 * creates temporary variables to create intermediate code
 * and pass it on the final code
 * 
 * @author Melina Zikou
 */
public class TempExprNode extends ExpressionNode{
    
    static int count = 0;
    int number = 0;
    public int offset;
    
    /**
     * constructor that creates a temporary variable
     * @param type the type of temp variable (float/int)
     */
    public TempExprNode(VariableType type){
        this.isTemp = true;
        this.type = type;
        this.number = ++count; 
    }

    /**
     * Convert to string for debug reasons
     * @return string
     */
    public String toString(){ 
        if(Main.Debug == true){
            return "t" + number + "[&" + offset + "]"; 
        }

        return "t" + number; 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
    }
}