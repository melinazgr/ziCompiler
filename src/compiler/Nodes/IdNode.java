package Nodes;
import Model.*;
import Main.*;

/**
 * Id Node Class
 * 
 * @author Melina Zikou
 */
public class IdNode extends ExpressionNode {
   
    public String id;
    public int idleft, idright;
    public int usedCount = 0;
    public int offset; // relative address in the current scope

    /**
     * constructor for id node
     * @param id the name of the variable
     * @param idleft row in the source code
     * @param idright column in the source code
     */
    public IdNode (String id, int idleft, int idright) {
        this.id = id;
        this.idleft = idleft;
        this.idright = idright;
        this.type = VariableType.NONE;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        if(Main.Debug == true){
            return id + "[&" + offset + "]"; 

        }
        return id; 
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * set type of the id node (float/int)
     * @param type the type of the id
     */
    public void setType(VariableType type){
        this.type = type;
    }

    /**
     * generates the code for the id 
     * @param cg where the code is generated and saved
     */
    public IdNode genTerm(CodeGenerator cg){
        return this;
    }

    /**
     * set the values of another id node to this id node
     * @param other other id node
     */
    public void setAlias(IdNode other){
        this.type = other.type;
        this.idleft = other.idleft;
        this.idright = other.idright;
        this.offset = other.offset;
    }
}