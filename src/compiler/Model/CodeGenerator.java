package Model;

import Nodes.*;

/**
 * Code generation interface
 * 
 * @author Melina Zikou
 */
public interface CodeGenerator {
    public void emitStatement(String operation, ExpressionNode expr1, ExpressionNode expr2, ExpressionNode resultExpr);
    public void emitJump(boolean condition, String operation, ExpressionNode expr1, ExpressionNode expr2, int label);
    public void emitJump(int label);
    public void emitLabel(int label);
    public void emitFunctionCall(String functionName, ExpressionNode expr);
    public String toString();
}

