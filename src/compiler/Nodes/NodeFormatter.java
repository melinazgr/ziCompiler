package Nodes;

public class NodeFormatter {
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
    
    public void incDepth(){
        depth += 4;
    }

    public void decDepth(){
        depth -= 4;

        if (depth < 0){
            depth = 0;
        }
    }

    public String getSpaces(){
        
        StringBuilder s = new StringBuilder();
        
        for(int i = 0; i < depth; i ++){
            s.append(" ");
        }
        
        return s.toString();
    }
}