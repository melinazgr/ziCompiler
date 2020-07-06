package Model;

import Nodes.*;
import java.util.*;

/**
 * Goal Code Generator
 * 
 * @author Melina Zikou
 */
public class CodeGeneratorMIXAL {

    StringBuilder code = new StringBuilder();
    ArrayList<NumberNode> constants = new ArrayList<NumberNode>();

    public CodeGeneratorMIXAL(){
    
    }

    /**
     * generate mixal code according to the ir code 
     * @param codeIR 
     * @throws Exception
     */
    public void genCode(CodeGeneratorIR codeIR)throws Exception{

        code.append(genBeforeProgr(codeIR));

        for(IntermediateCode ir: codeIR.code){
            if(ir.operation == "cast"){
                loadAValue(ir.expr1);
                code.append("\tFLOT\n");
                storeRegisterValue(ir.resultExpr, "A");
            }
            else if(ir.operation != null &&  ir.expr1 != null && ir.resultExpr != null  && ir.expr2 == null){
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
                    if(ir.resultExpr.type == VariableType.FLOAT){
                        storeRegisterValue(ir.resultExpr, "A");
                    }
                    else{
                        code.append("\tJANZ TOOLARGE\n");
                        storeRegisterValue(ir.resultExpr, "X");
                    }                   
                }
                else if(ir.operation == "/"){
                    if(ir.resultExpr.type == VariableType.INT){
                        loadAValue(ir.expr2);
                        code.append("\tJAZ ZERO\n");
                        loadAValue(ir.expr1);
                        code.append("\tSRAX 5\n");
                        operateAValue(ir.expr2, "DIV");
                        code.append("\tJOV TOOLARGE\n");
                        storeRegisterValue(ir.resultExpr, "A");      
                    }
                    else{
                        // loadAValue(ir.expr2);
                        // code.append("\tFCMP FZERO");
                        // code.append("\tJE ZERO");
                        loadAValue(ir.expr1);
                        operateAValue(ir.expr2, "DIV");
                        // TODO too large
                        storeRegisterValue(ir.resultExpr, "A");
                    }
                                  
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

                if(IntermediateCode.usedLabels.contains(ir.label)) {

                    int label = findLabelToUse(ir.label);
                    if(!IntermediateCode.createdLabels.contains(label)){
                        code.append("L"+ label);
                        IntermediateCode.createdLabels.add(label);
                    }
                }
            }
            else if(ir.operation == "jump"){
                int label = findLabelToUse(ir.label);

                code.append("\tJMP L"+ label+ "\n");
            }
        }

        code.append(genAfterProgr());
    }

    private int findLabelToUse(int label) {
        while (IntermediateCode.aliasLabels.containsKey(label)){
            int newLabel = IntermediateCode.aliasLabels.get(label);
            if(newLabel == label){
                break;
            }
            label = newLabel;
        }
        return label;
    }

    /**
     * Convert to string
     * @return string
     */
    public String toString(){
        return code.toString();
    }
    
    /**
     * comparison commands in mixal
     * @param expr the expression to be compared
     * @throws Exception
     */
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

    /**
     * jump operation in mixal
     * jump on grater / less / grater or equal / less or equal / equal / not equal
     * @param condition iffalse / iftrue
     * @param operation the operator (> / < / >= / <= / == / !=)
     * @param label the label to jump to
     * @throws Exception
     */
    private void jumpOnOperation(boolean condition, String operation, int label)throws Exception{
        if(condition){
           
            throw new Exception("jumpOnOperation: unsupported expression");
        }
        else {
            
            label = findLabelToUse(label);


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

    /**
     * load command in mixal
     * @param expr expression to load
     * @param negate if negate == true add '-'
     *               else dont do anything
     * @throws Exception
     */
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

    /**
     * store command in mixal
     * @param expr expression to store
     * @param register where to store this expression
     * @throws Exception
     */
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

    /**
     * operation commands in mixal
     * addition, multiplication, substruction, division
     * @param expr expression to be operated
     * @param operation the operation that is to happen
     * @throws Exception
     */
    private void operateAValue(ExpressionNode expr, String operation)throws Exception{
        String prefix = "";

        if(expr.type == VariableType.FLOAT) {
            prefix = "F";
        }

        if(expr instanceof NumberNode){
             NumberNode n = (NumberNode) expr;
             if(n.type == VariableType.INT){
                 if (n.intValue > 4095 || n.intValue < -4095){
                    constants.add(n);
                    code.append("\t" + prefix + operation +" "+ n.NodeId.toUpperCase());
                } 
                else{
                    code.append("\t" + prefix + operation + " =" + expr.toString() + "=");
                }
             }
             else{
                constants.add(n);
                code.append("\t" + prefix + operation +" "+ n.NodeId.toUpperCase());
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

    /**
     * default code before the program starts in mixal
     * @return the code
     */
    private String genBeforeProgr(CodeGeneratorIR codeIR){
        StringBuilder s = new StringBuilder();
        int terminal = 19;
        
        for(IntermediateCode ir: codeIR.code){
            if(codeIR.isMixBuilder == true){
                terminal = 18;
            }
        }


        s.append(
            "TERM\tEQU " + terminal + " \tthe terminal\n" +
            "SYM\tEQU 3000\n" +
            "\tORIG 50\n" +
            "BEGIN\tNOP\n"
            );
            
            return s.toString();
    }    

    /**
     * default code after the program starts in mixal
     * @return the code
     */
    private String genAfterProgr() throws Exception{
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
        
        s.append("FZERO\tCON 553648128\n");
        if(!constants.isEmpty()){
            for(NumberNode n: constants){
                s.append( n.NodeId.toUpperCase() + "\tCON ");

                if(n.type == VariableType.INT){
                    s.append(n.intValue + "\n");
                }

                else{
                    MixWord f = new MixWord();
                    s.append(f.getMixalFloat(n.floatValue) + " FLOAT = " + n.value + "\n");
                }
            }
        }
        
        s.append(
            "PRINT\tALF    - \n"+
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