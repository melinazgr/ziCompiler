package Nodes;

import Model.CodeGenerator;

/**
 * Expression Node Class
 * 
 * @author Melina Zikou
 */
public abstract class ExpressionNode extends Node{
    
    public VariableType type;
    public boolean isTemp =  false;

    public abstract void accept(Visitor v);

    public ExpressionNode reduce(CodeGenerator cg){
        return this;
    }

}