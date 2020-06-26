package Nodes;

import Model.*;

/**
 * Statement Node Class
 * 
 * @author Melina Zikou
 */
public abstract class StatementNode extends Node{
    
    public abstract void accept(Visitor v);

    public abstract void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType);

    public int after = 0;
}