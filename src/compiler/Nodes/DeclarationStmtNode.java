package Nodes;
import java.util.*;


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
    
}