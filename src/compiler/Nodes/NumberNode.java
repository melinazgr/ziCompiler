package Nodes;

/**
 * Null Statement Node 
 * 
 * @author Melina Zikou
 */
public class NumberNode extends ExpressionNode{
    
    public String value;
    public float floatValue;
    public int intValue;
    public int row, col;

    /**
     * constructor that creates number node
     * @param value value of number
     * @param row row in the source code
     * @param col column in the source code
     */
    public NumberNode (String value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col = col;

        if(value.indexOf(".") > 0){
            this.type = VariableType.FLOAT;
            this.floatValue = Float.parseFloat(value);
        }
        
        else{
            this.type = VariableType.INT;
            this.intValue = Integer.parseInt(value);
        }
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        return value; 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }
}