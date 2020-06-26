/* --------------------------Usercode Section------------------------ */
package Main;
import java_cup.runtime.*;
import java.io.*;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* the class created by JFlex will be named Lexer.*/
%class Lexer

%line
%column
    
/* CUP parser */
%cup
   
/* declaration of member variables and functions that are used inside
  scanner actions. */

%{ 

    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;
      
    public Lexer(FileReader fr, ComplexSymbolFactory sf){
	      this(fr);
        symbolFactory = sf;
    }

    public Symbol symbol(int code){
	      return symbolFactory.newSymbol(Integer.toString(code), code, new Location(yyline+1,yycolumn+1-yylength()),new Location(yyline+1,yycolumn+1));
    }

    public Symbol symbol(int code, String lexem){
	      return symbolFactory.newSymbol(Integer.toString(code), code, new Location(yyline+1, yycolumn +1), new Location(yyline+1,yycolumn+yylength()), lexem);
    }
    
    private void print_debug(String text) {
        if(Main.Debug){
            System.out.println(text);
        }  
    }
%}
   
%init{
    yybegin( DEFAULTPARSERSTATE );
%init}


/* Macro Declarations */

/* A line terminator is \r, \n , or \r\n. */

LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or line feed. */

WhiteSpace = {LineTerminator} | [\ \t]

num = "-"?[0-9]+("."[0-9]*)?    
id = [A-Za-z]([A-Za-z]|[0-9]|"_")*

%state DEFAULTPARSERSTATE COMMENTLINESTATE COMMENTBLOCKSTATE ERRORSTATE
   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/* regular expressions and actions */
   
   /* code section */
   <DEFAULTPARSERSTATE> {
    /* Return the token NULL-STMT declared in the class sym that was found. */
    ";"                {  print_debug(";");return symbol(sym.SEMICOLON); }
    {LineTerminator}   { }
    {WhiteSpace}       { }   
   
    /* Print  the token found that was declared in the class sym and then return it. */
    
      "+"                { return symbol(sym.ADD);}
      "-"                { return symbol(sym.MINUS);}
      "*"                { return symbol(sym.MULTI);}
      "/"                { return symbol(sym.DIV);}
      "("                { return symbol(sym.LPAREN);}
      ")"                { return symbol(sym.RPAREN);}
      "{"                { return symbol(sym.LCBRA);}
      "}"                { return symbol(sym.RCBRA);}
      "="                { return symbol(sym.ASSIGN);}
      "!="               { return symbol(sym.NOTEQUAL);}
      "=="               { return symbol(sym.EQUAL);}
      ">"                { return symbol(sym.GREAT);}
      "<"                { return symbol(sym.LESS);}
      "<="               { return symbol(sym.LESSQ);}
      ">="               { return symbol(sym.GREATQ);}
      "while"            { return symbol(sym.WHILE);}
      "for"              { return symbol(sym.FOR);}
      "if"               { return symbol(sym.IF);}
      "else"             { return symbol(sym.ELSE);}
      "println"          { return symbol(sym.PRINT);}
      "mainclass"        { return symbol(sym.MAINCLASS);}
      "public"           { return symbol(sym.PUBLIC);}
      "static"           { return symbol(sym.STATIC);}
      "void"             { return symbol(sym.VOID);}
      "main"             { return symbol(sym.MAIN);}
      "int"              { return symbol(sym.INT);}
      "float"            { return symbol(sym.FLOAT);}
      ","                { return symbol(sym.COMMA);}
    
      {num}              { return symbol(sym.NUM, yytext());}
      {id}               { return symbol(sym.ID, yytext());}

      "//"               {yybegin (COMMENTLINESTATE);}
      "/*"               {yybegin (COMMENTBLOCKSTATE);}

        .                {yybegin (ERRORSTATE); return symbol(sym.error);}
   }


  /* line comment section */
  <COMMENTLINESTATE> {
    {LineTerminator}   {yybegin( DEFAULTPARSERSTATE ); }
    .                  { }
  }

  /* block comment section */
  <COMMENTBLOCKSTATE> {
    "*/"             {yybegin( DEFAULTPARSERSTATE ); }
    {LineTerminator}   { }


    .                { }
  }

  /* error section */
  <ERRORSTATE> {
    ";"        {yybegin( DEFAULTPARSERSTATE ); return symbol(sym.SEMICOLON);}  
    .                { }
  }

    <<EOF>>              {return symbol(sym.EOF);}