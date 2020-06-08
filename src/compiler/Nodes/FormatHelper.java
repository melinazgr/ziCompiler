package Nodes;

public class FormatHelper {

    public static String getOpName(Operator op){
        switch(op) {
            case NOTEQUAL:
                return " != ";
            case EQUAL:
                return " == ";
            case GREAT:
                return " > ";
            case LESS:
                return " < ";
            case GREATQ:
                return " >= ";
            case LESSQ:
                return " <= ";
            default:
                return "";
        }
    }

    public static String getTypeName (VariableType t){
        switch(t) {
            case INT:
                return "int ";
            case FLOAT:
                return "float ";
            default:
                return "";
        }
    }
}