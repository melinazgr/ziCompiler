package Model;

public class CodeGeneratorMIXAL {

    StringBuilder code = new StringBuilder();

    public CodeGeneratorMIXAL(){
    
    }

    public void genCode(CodeGeneratorIR codeIR){
        
        
        code.append(genBeforeProgr());
        
        for(IntermediateCode ir: codeIR.code){

        }

        code.append(genAfterProgr());

    }

    public String toString(){
        return code.toString();
    }
    
    private String genBeforeProgr(){
        StringBuilder s = new StringBuilder();

        s.append("TERM\tEQU\t18\tthe terminal\n");
        s.append("BUFFER\tEQU\t1000\n");
        s.append("\tORIG\t3000\n");
        s.append("START\tEQU\t1000\n");
        s.append("BEGIN\tNOP\n");


        return s.toString();
    }    

    private String genAfterProgr(){
        StringBuilder s = new StringBuilder();

        s.append("\tHLT\n");
        s.append("\tEND\tBEGIN\n");


        return s.toString();
    }    

}