package Model;

import Nodes.*;
import java.util.*;

/**
 * Intermediate code Generator
 * ir = intermediate code
 * 
 * @author Melina Zikou
 */
public class CodeGeneratorIR implements CodeGenerator{

    public ArrayList<IntermediateCode> code = new ArrayList<IntermediateCode>();

    public CodeGeneratorIR(){
            
    }
    
    /**
     * add label to the ir code
     * @param label the label to be added
     */
    public void emitLabel(int label){
        IntermediateCode interCode = new IntermediateCode(label);
        code.add(interCode);
        interCode.toString();
    }

    /**
     * add statement to the ir code
     * @param operation the operation of the statement
     * @param expr1 one of the expressions of the statement
     * @param expr2 one of the expressions of the statement
     * @param resultExpr the result of the expression
     */
    public void emitStatement(String operation, ExpressionNode expr1, ExpressionNode expr2, ExpressionNode resultExpr){
        IntermediateCode interCode = new IntermediateCode(operation, expr1, expr2, resultExpr);
        code.add(interCode);
    }

    /**
     * add conditional jump to the ir code
     * @param condition iffalse / iftrue
     * @param expr1 one of the expressions of the statement
     * @param expr2 one of the expressions of the statement
     * @param label the label to jump to 
     */
    public void emitJump(boolean condition, String operation, ExpressionNode expr1, ExpressionNode expr2, int label){
        IntermediateCode interCode = new IntermediateCode(condition, operation, expr1, expr2, label);
        code.add(interCode);
    }

    /**
     * add jump to the ir code
     * @param label the label to jump to 
     */
    public void emitJump(int label){
        IntermediateCode interCode = new IntermediateCode("jump", label);
        code.add(interCode);
    }

    /**
     * emit function calls to the ir code from source code
     * @param functionName the name of the called function
     * @param expr expression called in the function
     */
    public void emitFunctionCall(String functionName, ExpressionNode expr){
        IntermediateCode interCode = new IntermediateCode(functionName, expr);
        code.add(interCode);
    }

     /**
     * Convert to string
     * @return string
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(IntermediateCode c: code){
            s.append(c.toString());
        }

        return s.toString();
    }

    public void optimize(){
            
        String prevOp = "";
        IntermediateCode prevNode = null;

        for(IntermediateCode ir : code){            
               
            if(ir.operation == "label"){

                if (prevOp == "label") {
                    ir.setAliasLabel(prevNode);
                }
                else{
                    ir.setAliasLabel(ir);
                }
            }

            prevNode = ir;
            prevOp = ir.operation;
        }
    }
}