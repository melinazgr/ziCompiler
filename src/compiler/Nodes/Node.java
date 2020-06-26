package Nodes;

/**
 * Node Class
 * 
 * @author Melina Zikou
 */
abstract public class Node {

    public static int nodeId = 0;
    public String NodeId;
    static int labels = 0;
    private Node parent;
    
    /**
     * constructor that gives each node an id
     * this id is used to print the AST
     */
    public Node(){
        this.NodeId = "node" + Integer.toString(++nodeId);
    }
    
    public abstract String toString();
    
    /**
     * creates unique label for a node used in code generation
     * each time the label is different (increased by one)
     * in order for the code to work properly
     * @return the label
     */
    public int newLabel(){
        return ++labels;
    }

    /**
     * set the parent of this node to given node
     * @param n the node to set as parent
     */
    public void setScopeParent (Node n){
        this.parent = n;
    }

    /**
     * get the parent of this node
     * @return the parent of this node
     */
    public Node getScopeParent(){
        return this.parent;
    }
}