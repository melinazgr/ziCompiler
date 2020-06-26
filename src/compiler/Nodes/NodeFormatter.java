package Nodes;

/**
 * Node Formatter Class
 * 
 * used to interpret the given source code correctly (tabs)
 * according to its content (loops, scopes)
 * 
 * @author Melina Zikou
 */
public class NodeFormatter {
    
    //this class is a singleton
    private static NodeFormatter singleInstance = null;

    int depth = 0;

    private NodeFormatter () {

    }

    public static NodeFormatter getInstance(){
        
        if(singleInstance == null){
            singleInstance = new NodeFormatter();
        }

        return singleInstance;
    }
    
    // increase spaces (1 tab = 4 spaces)
    public void incDepth(){
        depth += 4;
    }

    // decrease spaces (1 tab = 4 spaces)
    public void decDepth(){
        depth -= 4;

        if (depth < 0){
            depth = 0;
        }
    }

    // print out spaces/tabs
    public String getSpaces(){
        
        StringBuilder s = new StringBuilder();
        
        for(int i = 0; i < depth; i ++){
            s.append(" ");
        }
        
        return s.toString();
    }
}