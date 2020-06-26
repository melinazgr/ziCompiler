package Model;

import java.util.*; 
import Error.*;
import Nodes.*;

/**
 * Symbol Table class 
 * 
 * @author Melina Zikou
 */
public class SymbolTable {
    
    public  Map<String,IdNode> map = new HashMap<String,IdNode>();
    private SymbolTable parent;
    public static int count = 0;
    public int nextOffset = 0;
    public String tableId;

    public SymbolTable(){

    }
    
    /**
     * constructor of symbol table 
     * connecting the current symbol table
     * to it's parent, if there is one
     * 
     * parent and children in symbol table 
     * represent scopes
     * @param parent the parent of this symbol table
     */
    public SymbolTable(SymbolTable parent){
        this.parent = parent;
        if(parent != null){
            this.nextOffset = parent.getNextOffset();
        }
        this.tableId = "table"+Integer.toString(++count);
    }

    /**
     * get the parent of this symbol table
     * @return parent
     */
    public SymbolTable getParentSymbolTable (){
        return this.parent;
    }

    /**
     * get the next relative position of a variable (offset)
     * @return offset
     */
    public int getNextOffset(){
        return this.nextOffset;
    }
    
    /**
     * get the name of variable / id
     * @param key the name of the id
     * @return the value of the key
     */
    public IdNode get(String key){
        if(map.containsKey(key)){
            return map.get(key);
        }
        
        if(this.parent != null){
            return parent.get(key);
        }

        return null;
    }

    /**
     * add an id node to the map / symbol table
     * @param key the name of the id
     * @param value the value of the id
     */
    public void put(String key, IdNode value){
        if(map.containsKey(key)){
            ErrorHandler.getInstance().addError(String.format("Identifier '%s' already defined", key), 
                                                                key, 
                                                                value.idright, 
                                                                value.idleft);
        }

        else{
            value.offset = nextOffset;
            nextOffset++;
            map.put(key, value);
        }        
    }

    /**
     * print the symbol table
     * 
     * @return string with the symbol table
     */
    public String toString(){
        StringBuilder s = new StringBuilder();

        for (String key: map.keySet())
        {
            s.append(FormatHelper.getTypeName(map.get(key).type) + key + "\n");
        }

        return s.toString();
    }
}