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
            id.setScopeParent(n);
        }
    }

    public void visit(StatementListNode n){
        SymbolTable table = new SymbolTable(this.currSymbolTable);
        n.setSymbolTable(table);
        this.currSymbolTable = table;

        for(StatementNode stmt: n.statements){
            stmt.setScopeParent(n);
            stmt.accept(this);
        }

        for(IdNode id: this.currSymbolTable.map.values()){
            if (id.usedCount == 0){
                ErrorHandler.getInstance().addError(String.format("Variable '%s' defined but not used", id.id), id.id, id.idleft, id.idright);
            }
        }
        this.currSymbolTable = this.currSymbolTable.getParentSymbolTable();
    }

    public void visit(WhileStmtNode n){
        n.boolExpr.setScopeParent(n.getScopeParent());
        n.stmt.setScopeParent(n.getScopeParent());
        n.boolExpr.accept(this);    
        n.stmt.accept(this);    
    }

    public void visit(IfStmtNode n){
        n.boolExpr.setScopeParent(n.getScopeParent());
        n.stmt1.setScopeParent(n.getScopeParent());
        n.boolExpr.accept(this);    
        n.stmt1.accept(this);    

        if(n.stmt2 != null){
            n.stmt2.setScopeParent(n.getScopeParent());
            n.stmt2.accept(this);    
        }
    }

    public void visit(ForStmtNode n){
        n.opBoolExpr.setScopeParent(n.getScopeParent());
        n.opAssignExpr1.setScopeParent(n.getScopeParent());
        n.opAssignExpr2.setScopeParent(n.getScopeParent());
        n.stmt.setScopeParent(n.getScopeParent());

        n.opAssignExpr1.accept(this);    
        n.opAssignExpr2.accept(this);    
        n.opBoolExpr.accept(this);    
        n.stmt.accept(this); 
    }

    public void visit(AssignStmtNode n){
        n.expr.setScopeParent(n.getScopeParent());
        n.expr.accept(this);
    }

    public void visit(BoolExpressionNode n){
        n.expr1.setScopeParent(n.getScopeParent());
        n.expr2.setScopeParent(n.getScopeParent());
        n.expr1.accept(this);
        n.expr2.accept(this);

        n.type = VariableType.BOOL;
    }

    public void visit(SubstructionNode n){
        n.rval.setScopeParent(n.getScopeParent());
        n.term.setScopeParent(n.getScopeParent());
        n.rval.accept(this);
        n.term.accept(this);

        if(n.rval.type == VariableType.FLOAT || n.term.type == VariableType.FLOAT){
            n.type = VariableType.FLOAT;
        }
        else{
            n.type = VariableType.INT;
        }
    }

    public void visit(AdditionNode n){
        n.rval.setScopeParent(n.getScopeParent());
        n.term.setScopeParent(n.getScopeParent());
        n.rval.accept(this);
        n.term.accept(this);

        
        if(n.rval.type == VariableType.FLOAT || n.term.type == VariableType.FLOAT){
            n.type = VariableType.FLOAT;
        }
        else{
            n.type = VariableType.INT;
        }
    }

    public void visit(DivisionNode n){
        n.factor.setScopeParent(n.getScopeParent());
        n.term.setScopeParent(n.getScopeParent());
        n.factor.accept(this);
        n.term.accept(this);

        
        if(n.factor.type == VariableType.FLOAT || n.term.type == VariableType.FLOAT){
            n.type = VariableType.FLOAT;
        }
        else{
            n.type = VariableType.INT;
        }
    }

    public void visit(MultiNode n){
        n.factor.setScopeParent(n.getScopeParent());
        n.term.setScopeParent(n.getScopeParent());

        n.factor.accept(this);
        n.term.accept(this);

        if(n.factor.type == VariableType.FLOAT || n.term.type == VariableType.FLOAT){
            n.type = VariableType.FLOAT;
        }
        else{
            n.type = VariableType.INT;
        }
    }

    public void visit(NegateNode n){
        n.factor.setScopeParent(n.getScopeParent());
      
        n.factor.accept(this);
        n.type = n.factor.type;

    }

    public void visit(AssignExpressionNode n){
        n.expr.setScopeParent(n.getScopeParent());
        n.id.setScopeParent(n.getScopeParent());
        n.expr.accept(this);
        n.id.accept(this);

        n.type = n.id.type;

        if(n.id.type != n.expr.type){
            ErrorHandler.getInstance().addError(
                String.format("Not compatible types '%s', %s != %s", 
                    n.id.id, 
                    n.id.type, 
                    n.expr.type), 
                n.id.id, 
                n.id.idright, 
                n.id.idleft);
        }
    }

    public void visit(IdNode n){
        IdNode idNode = this.currSymbolTable.get(n.id);

        if(idNode == null){
            ErrorHandler.getInstance().addError(String.format("Undefined variable '%s'", n.id), n.id, n.idright, n.idleft);
        }
        else{
            idNode.usedCount++;
            n.setAlias(idNode);
        }
    }

    public void visit(NumberNode n){
        if(n.type == VariableType.INT){
            if(n.intValue > 1073741823 || n.intValue < -1073741823){
                ErrorHandler.getInstance().addError(String.format("Numeric value %s out of supported integer range", n.value), "num", n.row, n.col);
            }
        }
    }
   
    public void visit(NullStmtNode n){

    }

    public void visit(FunctionCallNode n){

    }
}