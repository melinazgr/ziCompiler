package Model;
import Nodes.*;
import Error.*;

public class SyntaxAnalysis implements Visitor {
    
    StringBuilder tree = new StringBuilder();

    public SyntaxAnalysis(){
    }

    public String toString(){
        StringBuilder s = new StringBuilder();

        s.append("\ndigraph g{ \nnode [shape = record,height=.1];\n");
        s.append(tree);
        s.append("}");
        
        return s.toString();
    }

    public void visit(ProgramNode n){
        
        tree.append(n.NodeId + "[label = \"<f0>  | <f2> " + n.id + "\"];\n");
        // tree.append("\""+n.NodeId+"\":f1 -> \"" + n.compStmt.NodeId + "\":f1");
        tree.append(String.format("\"%s\":f1->\"%s\":f0\n",n.NodeId, n.compStmt.NodeId));

        n.compStmt.accept(this);
    }

    public void visit(DeclarationStmtNode n){
    }

    public void visit(StatementListNode n){
        int count = 1;
        StringBuilder s = new StringBuilder();
        s.append(n.NodeId + "[label=\"");

        for(StatementNode stmt: n.statements){
            
            
            if (!(stmt instanceof DeclarationStmtNode)){
                if(count>1){
                    s.append("|");
                }
                s.append(String.format("<f%d>", count++));
    
                stmt.accept(this);
            }
        }
    
        s.append("\"];\n");
        
        count = 1;
        for(StatementNode stmt: n.statements){
            
            if (!(stmt instanceof DeclarationStmtNode)){
                s.append(String.format("\"%s\":f%d->\"%s\":f1;\n",n.NodeId, count++, stmt.NodeId));
            }
        }

        tree.append(s);

    }

    public void visit(WhileStmtNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1>%s|<f2>\"];\n", n.NodeId,  "while"));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.stmt.NodeId));

        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.boolExpr.NodeId));

        n.boolExpr.accept(this);    
        n.stmt.accept(this);    
    }

    public void visit(IfStmtNode n){
          
        if(n.stmt2 != null){
            tree.append(String.format("%s[label=\"<f0>|<f1>%s|<f2>|<f3>%s\"];\n", n.NodeId,  "if", "else"));
            tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.boolExpr.NodeId));
            tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.stmt1.NodeId));
            tree.append(String.format("\"%s\":f3->\"%s\":f1;\n", n.NodeId, n.stmt2.NodeId));

            n.boolExpr.accept(this);    
            n.stmt1.accept(this);  
            n.stmt2.accept(this);    
        }

        else{
            tree.append(String.format("%s[label=\"<f0>|<f1>%s|<f2>\"];\n", n.NodeId,  "if"));
            tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.boolExpr.NodeId));
            tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.stmt1.NodeId));


            n.boolExpr.accept(this);    
            n.stmt1.accept(this);  
        }
    }

    public void visit(ForStmtNode n){

        tree.append(String.format("%s[label=\"<f0>|<f1>%s|<f2>|<f3>|<f4>\"];\n", n.NodeId,  "for"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.opAssignExpr1.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.opBoolExpr.NodeId));
        tree.append(String.format("\"%s\":f3->\"%s\":f1;\n", n.NodeId, n.opAssignExpr2.NodeId));
        tree.append(String.format("\"%s\":f4->\"%s\":f1;\n", n.NodeId, n.stmt.NodeId));
        n.opAssignExpr1.accept(this);    
        n.opAssignExpr2.accept(this);    
        n.opBoolExpr.accept(this);    
        n.stmt.accept(this); 
    }
    public void visit(AssignStmtNode n){
        tree.append(String.format("%s[label=\"<f1>%s\"];\n", n.NodeId, "ASSIGN"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.expr.NodeId));

        
        n.expr.accept(this);
    }

    public void visit(BoolExpressionNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, FormatHelper.getOpName(n.operator,false)));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.expr1.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.expr2.NodeId));
        
        n.expr1.accept(this);
        n.expr2.accept(this);

    }

    public void visit(SubstructionNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, "-"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.rval.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.term.NodeId));
        n.rval.accept(this);
        n.term.accept(this);
    }
    public void visit(AdditionNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, "+"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.rval.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.term.NodeId));
        n.rval.accept(this);
        n.term.accept(this);
    }

    public void visit(DivisionNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, "\\"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.factor.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.term.NodeId));
        n.factor.accept(this);
        n.term.accept(this);
    }
    public void visit(MultiNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, "*"));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.factor.NodeId));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.term.NodeId));
        n.factor.accept(this);
        n.term.accept(this);
    }

    public void visit(NegateNode n){
        tree.append(String.format("%s[label=\"<f1> \\%s\"];\n",n.NodeId, "-"));
        tree.append(String.format("\"%s\":f1->\"%s\":f1;\n", n.NodeId, n.factor.NodeId));
        n.factor.accept(this);
    }

    public void visit(AssignExpressionNode n){
        tree.append(String.format("%s[label=\"<f0>|<f1> \\%s|<f2>\"];\n",n.NodeId, "="));
        tree.append(String.format("\"%s\":f2->\"%s\":f1;\n", n.NodeId, n.expr.NodeId));
        tree.append(String.format("\"%s\":f0->\"%s\":f1;\n", n.NodeId, n.id.NodeId));
        n.expr.accept(this);
        n.id.accept(this);
    }

    public void visit(IdNode n){
        tree.append(String.format("%s[label=\"<f0>%s\"];\n",n.NodeId, n.id));
    }

    public void visit(NumberNode n){
        tree.append(String.format("%s[label=\"<f0>%s\"];\n",n.NodeId, n.value.toString()));
    }
   
    public void visit(NullStmtNode n){

    }

    public void visit(FunctionCallNode n){
        tree.append(String.format("%s[label=\"<f0>%s\"];\n",n.NodeId, n.toString() ));
        

    }
    
}

