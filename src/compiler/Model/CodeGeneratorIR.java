package Model;

import Nodes.*;
import java.util.*;

public class CodeGeneratorIR implements CodeGenerator{

    public ArrayList<IntermediateCode> code = new ArrayList<IntermediateCode>();

    public CodeGeneratorIR(){
            
    }
    
    public void emitLabel(int label){
        IntermediateCode interCode = new IntermediateCode(label);
        code.add(interCode);
        interCode.toString();
    }

    public void emitStatement(String operation, ExpressionNode expr1, ExpressionNode expr2, ExpressionNode resultExpr){
        IntermediateCode interCode = new IntermediateCode(operation, expr1, expr2, resultExpr);
        code.add(interCode);
    }

    public void emitJump(boolean condition, String operation, ExpressionNode expr1, ExpressionNode expr2, int label){
        IntermediateCode interCode = new IntermediateCode(condition, operation, expr1, expr2, label);
        code.add(interCode);
    }

    public void emitJump(int label){
        IntermediateCode interCode = new IntermediateCode("jump", label);
        code.add(interCode);
    }

    public void emitFunctionCall(String functionName, ExpressionNode expr){
        IntermediateCode interCode = new IntermediateCode(functionName, expr);
        code.add(interCode);
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        for(IntermediateCode c: code){
            s.append(c.toString());
        }

        return s.toString();
    }

}