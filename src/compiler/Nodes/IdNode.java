package Nodes;
import Model.*;

public class IdNode extends ExpressionNode {
    public String id;
    public int idleft, idright;
    public int usedCount = 0;
    public int offset; // relative address in the current scope

    public IdNode (String id, int idleft, int idright) {
        this.id = id;
        this.idleft = idleft;
        this.idright = idright;
        this.type = VariableType.NONE;
    }

    public String toString () {
        return id + " [&" + offset + "]" ; 
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public void setType(VariableType type){
        this.type = type;
    }

    public IdNode genTerm(CodeGenerator cg){
        return this;
    }

    public void setAlias(IdNode other){
        this.type = other.type;
        this.idleft = other.idleft;
        this.idright = other.idright;
        this.offset = other.offset;
    }
}