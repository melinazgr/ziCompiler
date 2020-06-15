package Model;

import Nodes.*;
import java.util.*;

public class IntermediateCode {
    String operation, jumpOperation, functionName;
    int label;
    ExpressionNode expr1, expr2, resultExpr;
    boolean condition;

    public static HashSet<Integer> usedLabels = new HashSet<Integer>();

    public IntermediateCode(){

    }
    
    public IntermediateCode (String operation, ExpressionNode expr1, ExpressionNode expr2, ExpressionNode resultExpr){
        this.operation = operation;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.resultExpr = resultExpr;
    }

    public IntermediateCode (String operation, ExpressionNode expr1, ExpressionNode resultExpr){
        this.operation = operation;
        this.expr1 = expr1;
        this.resultExpr = resultExpr;
    }

    public IntermediateCode (int label){
        this.operation = "label";
        this.label = label;
    }

    public IntermediateCode (String jumpOperation, int label){
        this.operation = jumpOperation;
        this.label = label;
        usedLabels.add(label);
    }

    public IntermediateCode(boolean condition, String operation, ExpressionNode expr1, ExpressionNode expr2, int label){
        this.operation = operation;
        this.condition = condition;
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.label = label;
        usedLabels.add(label);
    }

    
    public IntermediateCode(String functionName, ExpressionNode expr1){
        this.expr1 = expr1;
        this.functionName = functionName;
        this.operation = "call";
    }

    public String toString(){
        if(operation == "label"){
            if(usedLabels.contains(label)){
                return "L" + label + ":\n";
            }
            
            return "";
        }
        else if(operation == "jump"){
            return "goto L" + label + "\n";
        }
        else if(operation == "call"){
            return "call " + functionName + " " + expr1.toString() + "\n";
        }
        else if (expr2 != null && expr1 != null && resultExpr != null && operation !=null){
            return  resultExpr.toString() +
                    " = " + expr1.toString() + 
                    " " + operation.toString() + 
                    " " + expr2.toString() + 
                    "\n";
            
        }
        
        else if (operation != null &&  expr1 != null && resultExpr != null){
            return  resultExpr.toString()+ 
                    " " + operation.toString() + 
                    " " + expr1.toString() + 
                    "\n";
        }
        else if (resultExpr == null && expr2 != null){
            return  "if" + condition + 
                    " " + expr1.toString() + 
                    " " + operation.toString() + 
                    " " + expr2.toString() + 
                    " goto " + "L" + label + "\n";
            
        }
        else if (jumpOperation != null){
            return "goto L" + label + "\n";
        }

        return "Unknown IR \n";
    }

}