package Nodes;
import java.util.*;


public class DeclarationNode extends StatementNode {
    VariableType type;
    ArrayList<String> list;

    public DeclarationNode (VariableType type, ArrayList<String> list) {
        this.type = type;
        this.list = list;
    }

    public String toString () {

        StringBuilder s = new StringBuilder(); 
        s.append(type.toString());

        for (String id : list) { 
                s.append(id);
                s.append(",");
            }
            
        return s.toString(); 
    }
    
    
}