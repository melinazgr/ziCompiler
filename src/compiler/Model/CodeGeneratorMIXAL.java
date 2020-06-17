package Model;

import Nodes.*;
import java.util.*;

public class CodeGeneratorMIXAL {

    StringBuilder code = new StringBuilder();
    ArrayList<NumberNode> constants = new ArrayList<NumberNode>();

    public CodeGeneratorMIXAL(){
    
    }

    private void loadAValue(ExpressionNode expr) throws Exception{
        if(expr instanceof NumberNode){
            NumberNode n = (NumberNode) expr;
            if(n.type == VariableType.INT){
                if (n.intValue > 4095 || n.intValue < -4095){
                    constants.add(n);
                    code.append("\tLDA " + n.NodeId.toUpperCase());
                } 
                else{
                    code.append("\tENTA " + expr.toString());
                }
            }

            else{
                constants.add(n);
                code.append("\tLDA " + n.NodeId.toUpperCase());
            }
        }
        else if(expr instanceof IdNode){
            code.append("\tLDA SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\tLDA SYM-" + ((TempExprNode) expr).offset);
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
            code.append("\t" + prefix + operation + " " + expr.toString());
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
                loadAValue(ir.expr1);
                storeRegisterValue(ir.resultExpr, "A");
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
                code.append("\tCHAR\n");
                code.append("\tSTA PRINT\n");
                code.append("\tSTX PRINT+1\n");
                code.append("\tOUT PRINT(TERM)\n");
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
            "PRINT\tEQU 2\n" +
            "\tORIG PRINT+24\n" +
            "BEGIN\tNOP\n"
            );

        return s.toString();
    }    

    private String genAfterProgr(){
        StringBuilder s = new StringBuilder();
        
        s.append(
            "\tHLT\n"+
            "TOOLARGE\tOUT OVERFLOW(TERM)\n" +
            "\tHLT 0\n" +
            "OVERFLOW\tALF OVER \n" +
	        "\tALF FLOW \n");
        
        for(int i = 0; i < 7; i++){
            s.append("\tALF      \n");
        }

        s.append(
            "ZERO\tOUT DIVZERO(TERM)\n" +
            "\tHLT 0\n" +
            "DIVZERO\tALF DIV  \n" +
            "\tALF ZERO \n");
            
        for(int i = 0; i < 7; i++){
            s.append("\tALF      \n");
        }
        
        for(NumberNode n: constants){
            s.append(n.NodeId.toUpperCase() + "\tCON "+ n.value + "\n");
            //TODO float conversion
        }

        s.append(
            "\tEND BEGIN\n"
        );

        return s.toString();
    }    

}