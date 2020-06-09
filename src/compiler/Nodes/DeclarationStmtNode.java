package Nodes;
import java.util.*;


public class DeclarationStmtNode extends StatementNode {
    public VariableType type;
    public ArrayList<String> list;

    public DeclarationStmtNode (VariableType type, ArrayList<String> list) {
        this.type = type;
        this.list = list;
    }

    public String toString () {

        StringBuilder s = new StringBuilder(); 
        s.append(FormatHelper.getTypeName(type));
        int count = 0;

        for (String id : list) { 
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