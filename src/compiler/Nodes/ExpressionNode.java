package Nodes;

public abstract class ExpressionNode extends Node{
    public VariableType type;

    public abstract void accept(Visitor v);
    
}