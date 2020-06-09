package Nodes;

public abstract class ExpressionNode extends Node{
    public abstract void accept(Visitor v);
    
}