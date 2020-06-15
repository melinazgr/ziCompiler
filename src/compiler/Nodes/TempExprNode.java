package Nodes;

public class TempExprNode extends ExpressionNode{
    static int count = 0;
    int number = 0;

    public TempExprNode(VariableType type){
        this.isTemp = true;
        this.type = type;
        this.number = ++count; 
    }

    public String toString(){ 
        return "t" + number; }

    public void accept(Visitor v) {
        
    }
}