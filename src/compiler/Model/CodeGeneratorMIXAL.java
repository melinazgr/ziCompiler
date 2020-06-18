package Model;

import Nodes.*;
import java.util.*;

public class CodeGeneratorMIXAL {

    StringBuilder code = new StringBuilder();
    ArrayList<NumberNode> constants = new ArrayList<NumberNode>();

    public CodeGeneratorMIXAL(){
    
    }

    private void compareAValue(ExpressionNode expr) throws Exception{
        

        if(expr instanceof NumberNode){
            NumberNode n = (NumberNode) expr;
            if (n.intValue > 4095 || n.intValue < -4095){
                constants.add(n);
                code.append("\tCMPA " + n.NodeId.toUpperCase());
            } 
            else{
                code.append("\tCMPA =" + expr.toString() + "=");
            }
            
        }
        else if(expr instanceof IdNode){
            code.append("\tCMPA SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\tCMPA SYM-" + ((TempExprNode) expr).offset);
        }
        else{
            throw new Exception("compareAValue: unsupported expression "+ expr.toString() + " " + expr.getClass());
        }
        code.append("\n");
    }

    private void jumpOnOperation(boolean condition, String operation, int label)throws Exception{
        if(condition){
           
            throw new Exception("jumOnOperation: unsupported expression");
        }
        else {
            if(operation == "EQUAL"){
                code.append("\tJNE L" + label);
            }
            else if(operation == "NOTEQUAL"){
                code.append("\tJE L" + label);
            }
            else if(operation == "GREAT"){
                code.append("\tJLE L" + label);
            }
            else if(operation == "GREATQ"){
                code.append("\tJL L" + label);
            }
            else if(operation == "LESSQ"){
                code.append("\tJG L" + label);
            }
            else if(operation == "LESS"){
                code.append("\tJGE L" + label);
            }
            else{
                throw new Exception("jumOnOperation: unsupported expression");
            }
        }
        
        code.append("\n");
    }

    private void loadAValue(ExpressionNode expr) throws Exception{
        loadAValue(expr, false);
    }

    private void loadAValue(ExpressionNode expr, boolean negate ) throws Exception{
        String LDA = "LDA", ENTA = "ENTA";
        if(negate){
            LDA = "LDAN";
            ENTA = "ENNA";
        }

        if(expr instanceof NumberNode){
            NumberNode n = (NumberNode) expr;
            if(n.type == VariableType.INT){
                if (n.intValue > 4095 || n.intValue < -4095){
                    constants.add(n);
                    code.append("\t" + LDA +" "+ n.NodeId.toUpperCase());
                } 
                else{
                    code.append("\t" + ENTA +" "+ expr.toString());
                }
            }

            else{
                constants.add(n);
                code.append("\t" + LDA + " " + n.NodeId.toUpperCase());
            }
        }
        else if(expr instanceof IdNode){
            code.append("\t" + LDA +" SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\t" + LDA +" SYM-" + ((TempExprNode) expr).offset);
        }
        else{
            throw new Exception("loadAValue: unsupported expression "+ expr.getClass());
        }

        code.append("\n");
    }

    private void storeRegisterValue(ExpressionNode expr, String register) throws Exception{
        if(expr instanceof IdNode){
            code.append("\tST" + register + " SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\tST" + register + " SYM-" + ((TempExprNode) expr).offset);
        }
        else{
            throw new Exception("storeAValue: unsupported expression "+ expr.getClass());
        }

        code.append("\n");
    }

    private void operateAValue(ExpressionNode expr, String operation)throws Exception{
        String prefix = "";

        if(expr.type == VariableType.FLOAT) {
            prefix = "F";
        }

        if(expr instanceof NumberNode){
             NumberNode n = (NumberNode) expr;
            if (n.intValue > 4095 || n.intValue < -4095){
                constants.add(n);
                code.append("\t" + prefix + operation + n.NodeId.toUpperCase());
            } 
            else{
                code.append("\t" + prefix + operation + " =" + expr.toString() + "=");
            }
        }
        else if(expr instanceof IdNode){
            code.append("\t" + prefix + operation + " SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\t" + prefix + operation + " SYM-" + ((TempExprNode) expr).offset);
        }
        else{
            throw new Exception("operateAValue: unsupported expression "+ expr.toString() + " " + expr.getClass());
        }

        code.append("\n");
    }

    public void genCode(CodeGeneratorIR codeIR)throws Exception{
        
        
        code.append(genBeforeProgr());
        
        for(IntermediateCode ir: codeIR.code){
            if(ir.operation != null &&  ir.expr1 != null && ir.resultExpr != null  && ir.expr2 == null){
                if(ir.operation == "-"){
                    loadAValue(ir.expr1, true);
                    storeRegisterValue(ir.resultExpr, "A");
                }
                else{
                    loadAValue(ir.expr1);
                    storeRegisterValue(ir.resultExpr, "A");
                }
            }
            else if (ir.operation !=null && ir.expr1 != null && ir.resultExpr != null && ir.expr2 != null){
                              
                if(ir.operation == "+"){
                    loadAValue(ir.expr1);
                    operateAValue(ir.expr2, "ADD");
                    code.append("\tJOV TOOLARGE\n");
                    storeRegisterValue(ir.resultExpr, "A");
                }
                else if(ir.operation == "-"){
                    loadAValue(ir.expr1);
                    operateAValue(ir.expr2, "SUB");
                    code.append("\tJOV TOOLARGE\n");
                    storeRegisterValue(ir.resultExpr, "A");
                }
                else if(ir.operation == "*"){
                    loadAValue(ir.expr1);
                    operateAValue(ir.expr2, "MUL");
                    code.append("\tJANZ TOOLARGE\n");
                    storeRegisterValue(ir.resultExpr, "X");
                }
                else if(ir.operation == "/"){
                    loadAValue(ir.expr2);
                    code.append("\tJAZ ZERO\n");
                    loadAValue(ir.expr1);
                    code.append("\tSRAX 5\n");
                    operateAValue(ir.expr2, "DIV");
                    code.append("\tJOV TOOLARGE\n");
                    storeRegisterValue(ir.resultExpr, "A");
                }
            }
            else if(ir.operation == "call"){
                loadAValue(ir.expr1);
                code.append("\tJMP PRINTLN\n");
            }

            else if(ir.resultExpr == null && ir.expr2 != null){
                loadAValue(ir.expr1);
                compareAValue(ir.expr2);
                jumpOnOperation(ir.condition, ir.operation, ir.label);
            }
            else if(ir.operation == "label"){
                if(IntermediateCode.usedLabels.contains(ir.label)){
                    code.append("L"+ ir.label+ "");
                }
            }
            else if(ir.operation == "jump"){
                code.append("\tJMP L"+ ir.label+ "\n");
            }
            else if(ir.jumpOperation != null){
                code.append("L"+ ir.label+ "");
            }
        }

        code.append(genAfterProgr());

    }

    public String toString(){
        return code.toString();
    }
    
    private String genBeforeProgr(){
        StringBuilder s = new StringBuilder();

        s.append(
            "TERM\tEQU 19\tthe terminal\n" +
            "SYM\tEQU 3000\n" +
            "\tORIG 3000\n" +
            "BEGIN\tNOP\n"
            );

        return s.toString();
    }    

    private String genAfterProgr(){
        StringBuilder s = new StringBuilder();
        
        s.append("\tHLT\n");

        s.append(
            "PRINTLN\tSTJ PRINTEX\n" +
            "\tCMPA 0\n" +
            "\tCHAR\n" +
            "\tSTA PRINT1\n" +
            "\tSTX PRINT2\n" +
            "\tJGE 1F\n" +
            "\tOUT PRINT(TERM)\n" +
            "\tJMP PRINTEX\n" +
            "1H\tOUT PRINT1(TERM)\n" +
            "PRINTEX\tJMP *\n");

        s.append(
            "TOOLARGE\tOUT OVERFLOW(TERM)\n" +
            "\tHLT 0\n" +
            "OVERFLOW\tALF OVER \n" +
            "\tALF FLOW \n");
        
        for(int i = 0; i < 24; i++){
            s.append("\tALF      \n");
        }

        s.append(
            "ZERO\tOUT DIVZERO(TERM)\n" +
            "\tHLT 0\n" +
            "DIVZERO\tALF DIV  \n" +
            "\tALF ZERO \n");
            
        for(int i = 0; i < 24; i++){
            s.append("\tALF      \n");
        }
        
        for(NumberNode n: constants){
            s.append(n.NodeId.toUpperCase() + "\tCON "+ n.value + "\n");
            //TODO float conversion
        }

        s.append(
            "PRINT\tALF   - \n"+
            "PRINT1\tCON 0\n" +
            "PRINT2\tCON 0\n");

        for(int i = 0; i < 24; i++){
            s.append("\tALF      \n");
        }
        
        s.append(  
            "\tEND BEGIN\n"
        );

        return s.toString();
    }    

}