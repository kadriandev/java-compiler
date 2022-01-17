/*
  File Name: tiny.flex
  JFlex specification for the TINY language
*/

import java.util.ArrayList;
   
%%
   
%class Lexer
%type Token
%line
%column
    
%eofval{
  //System.out.println("*** Reaching end of file");
  return null;
%eofval};

%{
  private static ArrayList<String> tagStack = new ArrayList<String>();

  // static method such as getTagName can be defined here as well
%};

/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or form feed. */
WhiteSpace     = {LineTerminator} | [ \t\f]
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
digit = [0-9]
number = {digit}+
   
/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
letter = [a-zA-Z]
identifier = {letter}+
   
%%
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
"if"               { return new Token(Token.IF, yytext(), yyline, yycolumn); }
"then"             { return new Token(Token.THEN, yytext(), yyline, yycolumn); }
"else"             { return new Token(Token.ELSE, yytext(), yyline, yycolumn); }
"end"              { return new Token(Token.END, yytext(), yyline, yycolumn); }
"repeat"           { return new Token(Token.REPEAT, yytext(), yyline, yycolumn); }
"until"            { return new Token(Token.UNTIL, yytext(), yyline, yycolumn); }
"read"             { return new Token(Token.READ, yytext(), yyline, yycolumn); }
"write"            { return new Token(Token.WRITE, yytext(), yyline, yycolumn); }
":="               { return new Token(Token.ASSIGN, yytext(), yyline, yycolumn); }
"="                { return new Token(Token.EQ, yytext(), yyline, yycolumn); }
"<"                { return new Token(Token.LT, yytext(), yyline, yycolumn); }
">"                { return new Token(Token.GT, yytext(), yyline, yycolumn); }
"+"                { return new Token(Token.PLUS, yytext(), yyline, yycolumn); }
"-"                { return new Token(Token.MINUS, yytext(), yyline, yycolumn); }
"*"                { return new Token(Token.TIMES, yytext(), yyline, yycolumn); }
"/"                { return new Token(Token.OVER, yytext(), yyline, yycolumn); }
"("                { return new Token(Token.LPAREN, yytext(), yyline, yycolumn); }
")"                { return new Token(Token.RPAREN, yytext(), yyline, yycolumn); }
";"                { return new Token(Token.SEMI, yytext(), yyline, yycolumn); }
{number}           { return new Token(Token.NUM, yytext(), yyline, yycolumn); }
{identifier}       { return new Token(Token.ID, yytext(), yyline, yycolumn); }
{WhiteSpace}+      { /* skip whitespace */ }   
"{"[^\}]*"}"       { /* skip comments */ }
.                  { return new Token(Token.ERROR, yytext(), yyline, yycolumn); }
