package Nodes;

abstract public class Node {

    public static int nodeId = 0;
    public String NodeId;

    public Node(){
        this.NodeId = "node"+Integer.toString(++nodeId);
    }
    public abstract String toString();
    
}