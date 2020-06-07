/* --------------------------Usercode Section------------------------ */
import java_cup.runtime.*;

%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* The name of the class JFlex will create will be Lexer.*/
%class Lexer

/* The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.*/
%line
%column
    
/* CUP parser */
%cup
   
/* declaration of member variables and functions that are used inside
  scanner actions. */
%{   
    /* create java_cup.runtime.Symbol with information 
       about the current token, with no value*/
       
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* create java_cup.runtime.Symbol with information 
       about the current token, with value*/

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/* Macro Declarations */
   
/* A line terminator is \r, \n , or \r\n. */

LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or line feed. */

WhiteSpace = {LineTerminator} | [ \t\f]
   

num = [0-9]*("."[0-9]*)?    
id = [A-Za-z]([A-Za-z]|[0-9]|"_")*

   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/* regular expressions and actions */
   
   /* YYINITIAL is the state at which the lexer begins scanning */
   
<YYINITIAL> {
   
    /* Return the token NULL-STMT declared in the class sym that was found. */
    ";"                { System.out.println(";");return symbol(sym.SEMICOLON); }
    {LineTerminator}   { }
    {WhiteSpace}       { }   
   
    /* Print the token found that was declared in the class sym and then return it. */

      "+"                { System.out.println(" + "); return symbol(sym.ADD); }
      "-"                { System.out.println(" - "); return symbol(sym.SUBSTRACT); }
      "*"                { System.out.println(" * "); return symbol(sym.MULTI); }
      "/"                { System.out.println(" / "); return symbol(sym.DIV); }
      "("                { System.out.println(" ( "); return symbol(sym.LPAREN); }
      ")"                { System.out.println(" ) "); return symbol(sym.RPAREN); }
      "{"                { System.out.println(" { "); return symbol(sym.LCBRA); }
      "}"                { System.out.println(" } "); return symbol(sym.RCBRA); }
      "="                { System.out.println(" = "); return symbol(sym.ASSIGN); }
      "!="               { System.out.println(" != "); return symbol(sym.NOTEQUAL); }
      "=="               { System.out.println(" == "); return symbol(sym.EQUAL); }
      ">"                { System.out.println(" > "); return symbol(sym.GREAT); }
      "<"                { System.out.println(" < "); return symbol(sym.LESS); }
      "<="               { System.out.println(" <= "); return symbol(sym.LESSQ); }
      ">="               { System.out.println(" >= "); return symbol(sym.GREATQ); }
      "while"            { System.out.println(" while "); return symbol(sym.WHILE); }
      "for"              { System.out.println(" for "); return symbol(sym.FOR); }
      "if"               { System.out.println(" if "); return symbol(sym.IF); }
      "else"             { System.out.println(" else "); return symbol(sym.ELSE); }
      "println"          { System.out.println(" println "); return symbol(sym.PRINT); }
      "mainclass"        {System.out.println(" mainclass "); return symbol(sym.MAINCLASS);}
      "public"           {System.out.println(" public "); return symbol(sym.PUBLIC);}
      "static"           {System.out.println(" static "); return symbol(sym.STATIC);}
      "void"             {System.out.println(" void "); return symbol(sym.VOID);}
      "main"             {System.out.println(" main "); return symbol(sym.MAIN);}
      "int"              {System.out.println(" int "); return symbol(sym.INT);}
      "float"            {System.out.println(" float "); return symbol(sym.FLOAT);}
      ","                {System.out.println(" , "); return symbol(sym.COMMA);}
    

    {num}              { System.out.println(yytext() );return symbol(sym.NUM); }
    {id}               { System.out.println(yytext() );return symbol(sym.ID) ; }

     }
