package Nodes;

import java.util.*;
import Model.*;

public class DeclarationStmtNode extends StatementNode {
    public VariableType type;
    public ArrayList<IdNode> list;

    public DeclarationStmtNode (VariableType type, ArrayList<IdNode> list) {
        
        this.type = type;
        this.list = list;

        for(IdNode id : list){
            id.setType(type);
        }
    }

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
    
    public void accept(Visitor v) {
        v.visit(this);
    }

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