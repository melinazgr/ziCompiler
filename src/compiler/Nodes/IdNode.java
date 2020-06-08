package Nodes;

public class IdNode extends ExpressionNode {
    String id;
    int idleft, idright;

    public IdNode (String id, int idleft, int idright) {
        this.id = id;
        this.idleft = idleft;
        this.idright = idright;
    }

    public String toString () {
        return id + "["+ idleft+", " + idright + "]"; 
    }
}