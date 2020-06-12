package Nodes;

public class IdNode extends ExpressionNode {
    public String id;
    public int idleft, idright;
    public int usedCount = 0;

    public IdNode (String id, int idleft, int idright) {
        this.id = id;
        this.idleft = idleft;
        this.idright = idright;
        this.type = VariableType.NONE;

    }

    public String toString () {
        return id; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public void setType(VariableType type){
        this.type = type;
    }
}