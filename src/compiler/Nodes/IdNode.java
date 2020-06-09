package Nodes;

public class IdNode extends ExpressionNode {
    public String id;
    public int idleft, idright;

    public IdNode (String id, int idleft, int idright) {
        this.id = id;
        this.idleft = idleft;
        this.idright = idright;
    }

    public String toString () {
        return id + "["+ idleft+", " + idright + "]"; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}