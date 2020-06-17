package Model;

import Nodes.*;

public class CodeGeneratorMIXAL {

    StringBuilder code = new StringBuilder();

    public CodeGeneratorMIXAL(){
    
    }

    private void loadAValue(ExpressionNode expr) throws Exception{
        if(expr instanceof NumberNode){
            code.append("\tENTA " + expr.toString());
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

    private void storeAValue(ExpressionNode expr) throws Exception{
        if(expr instanceof IdNode){
            code.append("\tSTA SYM+" + ((IdNode) expr).offset);
        }
        else if(expr instanceof TempExprNode){
            code.append("\tSTA SYM-" + ((TempExprNode) expr).offset);
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
                storeAValue(ir.resultExpr);
            }
            else if (ir.operation !=null && ir.expr1 != null && ir.resultExpr != null && ir.expr2 != null){
                loadAValue(ir.expr1);
                
                if(ir.operation == "+"){
                    operateAValue(ir.expr2, "ADD");
                }
                else if(ir.operation == "-"){
                    operateAValue(ir.expr2, "SUB");
                }

                storeAValue(ir.resultExpr);
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
            "TERM\tEQU 18\tthe terminal\n" +
            "SYM\tEQU 3000\n" +
            "PRINT\tEQU 2\n" +
            "\tORIG PRINT+24\n" +
            "BEGIN\tNOP\n"
            );


        return s.toString();
    }    

    private String genAfterProgr(){
        StringBuilder s = new StringBuilder();

        s.append("\tHLT\n");
        s.append("\tEND BEGIN\n");


        return s.toString();
    }    

}