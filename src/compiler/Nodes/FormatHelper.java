package Nodes;

public class FormatHelper {

    public static String getOpName(Operator op, boolean addSpaces){

        String s;
        switch(op) {
            case NOTEQUAL:
                s = "!=";
                break;
            case EQUAL:
                s =  "==";
                break;
            case GREAT:
                s = ">";
                break;
            case LESS:
                s = "<";
                break;
            case GREATQ:
                s = ">=";
            break;
                case LESSQ:
                s = "<=";
            break;    
            default:
                return "";
        }

        if (addSpaces)
        {
            return " " + s + " ";
        }

        return s;
    }

    public static String getOpName(Operator op){
        return getOpName(op, true);
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