package Model;
import java.util.*; 

public class SymbolTable {
    private Map<String,Object> map = new HashMap<String,Object>();
    private SymbolTable parent;

    public SymbolTable(){

    }

    public SymbolTable(SymbolTable parent){
        this.parent = parent;
    }

    public SymbolTable getParentSymbolTable (){
        return this.parent;
    }
    
    public Object get(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }
        
        if(this.parent != null){
            return parent.get(key);
        }

        return null;
    }

    public void put(String key, Object value){
        if(map.containsKey(key)){
            System.out.printf("Error, identifier %s, already defined at line %d, column %d.", key, 0, 0);
        }
        else{
            map.put(key, value);
        }        
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        for (String key: map.keySet())
        {
            s.append(key + "\n");
            //TODO more info for the id
        }

        return s.toString();
    }
}