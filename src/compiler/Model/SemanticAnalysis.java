package Model;
import Nodes.*;
import Error.*;

public class SemanticAnalysis implements Visitor {
    
    SymbolTable currSymbolTable;
    public SemanticAnalysis(){
    }

    public void visit(ProgramNode n){
        n.compStmt.accept(this);
    }

    public void visit(DeclarationStmtNode n){
        for (IdNode id : n.list) { 
            currSymbolTable.put(id.id, id);               
        }
    }

    public void visit(StatementListNode n){
        SymbolTable table = new SymbolTable(this.currSymbolTable);
        n.setSymbolTable(table);
        this.currSymbolTable = table;

        for(StatementNode stmt: n.statements){
            stmt.accept(this);
        }

        this.currSymbolTable = this.currSymbolTable.getParentSymbolTable();
    }

    public void visit(WhileStmtNode n){
        n.boolExpr.accept(this);    
        n.stmt.accept(this);    
    }

    public void visit(IfStmtNode n){
        n.boolExpr.accept(this);    
        n.stmt1.accept(this);    

        if(n.stmt2 != null){
            n.stmt2.accept(this);    
        }
    }

    public void visit(ForStmtNode n){
        n.opAssignExpr1.accept(this);    
        n.opAssignExpr2.accept(this);    
        n.opBoolExpr.accept(this);    
        n.stmt.accept(this); 
    }

    public void visit(AssignStmtNode n){
        n.expr.accept(this);
    }

    public void visit(BoolExpressionNode n){
        n.expr1.accept(this);
        n.expr2.accept(this);
    }

    public void visit(SubstructionNode n){
        n.rval.accept(this);
        n.term.accept(this);
    }

    public void visit(AdditionNode n){
        n.rval.accept(this);
        n.term.accept(this);
    }

    public void visit(DivisionNode n){
        n.factor.accept(this);
        n.term.accept(this);
    }

    public void visit(MultiNode n){
        n.factor.accept(this);
        n.term.accept(this);
    }

    public void visit(NegateNode n){
        n.factor.accept(this);
    }

    public void visit(AssignExpressionNode n){
        n.expr.accept(this);
        n.id.accept(this);
    }

    public void visit(IdNode n){
        Object o = this.currSymbolTable.get(n.id);

        if(o == null){
            ErrorHandler.getInstance().addError(String.format("Undefined variable '%s'", n.id), n.id, n.idright, n.idleft);
        }
    }

    public void visit(NumberNode n){

    }
   
    public void visit(NullStmtNode n){

    }

    public void visit(FunctionCallNode n){

    }
}