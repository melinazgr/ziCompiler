package Nodes;

/**
 * Visitor pattern
 * 
 * @author Melina Zikou
 */
public interface Visitor {
    public void visit(ProgramNode n);
    public void visit(DeclarationStmtNode n);
    public void visit(StatementListNode n);
    public void visit(WhileStmtNode n);
    public void visit(NullStmtNode n);
    public void visit(IfStmtNode n);
    public void visit(FunctionCallNode n);
    public void visit(ForStmtNode n);
    public void visit(AssignStmtNode n);
    public void visit(BoolExpressionNode n);
    public void visit(SubstructionNode n);
    public void visit(NumberNode n);
    public void visit(NegateNode n);
    public void visit(AdditionNode n);
    public void visit(AssignExpressionNode n);
    public void visit(IdNode n);
    public void visit(DivisionNode n);
    public void visit(MultiNode n);
}