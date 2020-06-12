package Nodes;

public class NumberNode extends ExpressionNode{
    public String value;
    public float floatValue;
    public int intValue;
    public int row, col;


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

    public String toString () {
        return value; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}