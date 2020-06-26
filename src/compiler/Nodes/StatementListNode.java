package Nodes;

import Model.*;
import java.util.*;

/**
 * Statement List Node Class
 * 
 * @author Melina Zikou
 */
public class StatementListNode extends StatementNode{
    
    //list with statements
    public ArrayList <StatementNode> statements = new ArrayList <StatementNode>();
    
    static int depth = 0;
    private SymbolTable table;

    //the temporary variables that are available for usage
    public Stack<TempExprNode> tempNodes = new Stack<TempExprNode>();
    
    //all the total temporary variables that have been created
    public Stack<TempExprNode> createdTempNodes = new Stack<TempExprNode>();

    /**
     * Convert to string
     * @return string
     */
    public String toString () {
        
        StringBuilder s = new StringBuilder(); 

        NodeFormatter.getInstance().incDepth();
        
        for (StatementNode statement : statements) { 
            s.append(NodeFormatter.getInstance().getSpaces());
            s.append(statement.toString());
            s.append("\n");
        }

        NodeFormatter.getInstance().decDepth();

        return s.toString();
    }

    /**
     * add a statement to the list
     * @param stmt the statement to be added
     */
    public void addStatement(StatementNode stmt) {
        statements.add(stmt);
    }

    /**
     * accept function for the visitor
     * @param v the visitor
     */
    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * set the symbol table for the certain statement list
     * @param table the table to be set
     */
    public void setSymbolTable(SymbolTable table){
        this.table = table;
    }

    /**
     * get the symbol table of this statement list
     * @return the symbol table
     */
    public SymbolTable getSymbolTable(){
        return this.table;
    }

    /**
     * generates code
     * @param cg where the code is saved
     * @param begin starting label 
     * @param after ending label
     * @param stmtGenType type of statement
     */
    public void genCode(CodeGenerator cg, int begin, int after, StatementTypeGeneration stmtGenType) {
        
        for (StatementNode statement : statements) { 
            if(statement instanceof DeclarationStmtNode && stmtGenType == StatementTypeGeneration.DECL_SKIP){
                continue;
            }

            if(!(statement instanceof DeclarationStmtNode) && stmtGenType == StatementTypeGeneration.DECL_ONLY){    
                continue;
            }

            int label1 = newLabel();
            int label2 = newLabel();

            cg.emitLabel(label1);
            statement.genCode(cg, label1, label2, stmtGenType);
            cg.emitLabel(label2);      
        }
    } 

    /**
     * gets the number of all the temporary variables
     * used up to this point of the program
     * irrelevant to the scope
     * @return the number described before
     */
    public int getTotalTempVarSize(){
        int offset = createdTempNodes.size();
        if(this.getScopeParent() != null){
            offset += ((StatementListNode) this.getScopeParent()).getTotalTempVarSize();
        }

        return offset;
    }

    /**
     * create new temporary variables if none available for use
     * @param type the type of the variable
     * @return the temporary variable selected for usage
     */
    public TempExprNode getTemp(VariableType type){
        if(tempNodes.isEmpty()){
            TempExprNode temp = new TempExprNode(type);
            createdTempNodes.push(temp);
            temp.offset = getTotalTempVarSize(); 
            return temp;
        } 

        return tempNodes.pop();
    }

    /**
     * add to list of available variables a new variable
     * @param temp the temporary variable to be added to the list
     */
    public void returnTemp(TempExprNode temp){
        tempNodes.push(temp);
    }
}