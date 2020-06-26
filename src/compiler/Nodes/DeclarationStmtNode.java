package Nodes;

import java.util.*;
import Model.*;

/**
 * Declare Statement Statement Node Class
 * 
 * @author Melina Zikou
 */
public class DeclarationStmtNode extends StatementNode {
    
    public VariableType type;
    public ArrayList<IdNode> list;

    /**
     * constructor creating declaration statement node
     * @param type the type of the variable (float/int)
     * @param list the list where the variables of declaration are saved
     */
    public DeclarationStmtNode (VariableType type, ArrayList<IdNode> list) {
        
        this.type = type;
        this.list = list;

        for(IdNode id : list){
            id.setType(type);
        }
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString () {

        StringBuilder s = new StringBuilder(); 
        s.append(FormatHelper.getTypeName(type));
        int count = 0;

        for (IdNode id : list) { 
                if(count > 0) {
                    s.append(",");
                }
                s.append(id);
                count++;               
            }
            
        return s.toString() + ";"; 
    }
    
    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v) {
        v.visit(this);
    }

    /**
     * generates code
     * @param cg where the code is saved
     * @param begin starting label 
     * @param after ending label
     * @param stmtGenType type of statement
     */
    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType){

        for(IdNode idNode: list){
            if(idNode.type == VariableType.FLOAT){
                cg.emitStatement("=", new NumberNode("0.0", idNode.idleft, idNode.idright), null, idNode);
            }
            else{
                cg.emitStatement("=", new NumberNode("0", idNode.idleft, idNode.idright), null, idNode);
            }          
        }
    } 
}