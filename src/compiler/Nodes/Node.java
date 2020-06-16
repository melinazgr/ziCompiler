package Nodes;

abstract public class Node {

    public static int nodeId = 0;
    public String NodeId;
    static int labels = 0;
    private Node parent;

    public Node(){
        this.NodeId = "node"+Integer.toString(++nodeId);
    }
    
    public abstract String toString();
    
    public int newLabel(){
        return ++labels;
    }

    public void setScopeParent (Node n){
        this.parent = n;
    }

    public Node getScopeParent(){
        return this.parent;
    }
}