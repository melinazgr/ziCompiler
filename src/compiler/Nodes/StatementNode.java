package Nodes;

public abstract class StatementNode extends Node{
    public abstract void accept(Visitor v);
}