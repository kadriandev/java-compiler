import java.io.FileOutputStream;
import java.io.IOException;

import absyn.*;
import absyn.Declaration.*;
import absyn.Expressions.*;
import absyn.Statements.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;
  private FileOutputStream outfile;
  private boolean error = false;

  public ShowTreeVisitor(Absyn result, FileOutputStream out) {
    this.outfile = out;
    printMessage("The abstract syntax tree is:", 0);
    result.accept(this, 0); 
  }

  public boolean hasSytaxErrors(){
    return error;
  }

  private void printMessage(String message, int level) {
      indent(level);
      try{
        this.outfile.write((message + "\n").getBytes());
      }catch(IOException e) {
        System.err.println("Error writing to output file");
      }
      //System.out.println(message);
  }

  private void indent( int level ) {
    try{
      for( int i = 0; i < level * SPACES; i++ ) this.outfile.write(" ".getBytes());
    }catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void visit( DeclarationList expList, int level ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( StatementList expList, int level ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( AssignExpression exp, int level ) {
    printMessage("AssignExp:", level);
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfStatement exp, int level ) {
    printMessage("IfExp:", level);
    level++;
    exp.test.accept( this, level );
    printMessage("", level);
    exp.ifblock.accept( this, level );
    
    if (exp.elseblock != null ) {
      printMessage("ElseExp:", --level); 
      exp.elseblock.accept( this, ++level );
    }
  }

  public void visit( IntExpression exp, int level ) {
    printMessage("IntExp: " + exp.value, level);
  }

  public void visit( OpExpression exp, int level ) {
    String message = "OpExp:";
    switch( exp.op ) {
      case OpExpression.PLUS:
        message += " + ";
        break;
      case OpExpression.MINUS:
        message += " - ";
        break;
      case OpExpression.TIMES:
        message += " * ";
        break;
      case OpExpression.OVER:
        message += " / ";
        break;
      case OpExpression.EQ:
        message += " = ";
        break;
      case OpExpression.LT:
        message +=  " < ";
        break;
      case OpExpression.GT:
        message += " > ";
        break;
      default:
        message += "Unrecognized operator at line " + exp.row + " and column " + exp.col;
    }
    printMessage(message, level);
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( WhileStatement exp, int level ) {
    printMessage("WhileExp:", level);
    level++;
    exp.test.accept( this, level );
    printMessage("", level);
    exp.exps.accept( this, level );
  }

  public void visit( VarExpression exp, int level ) {
    String varStr = exp.name;
    if(exp.index != null) {
      varStr += "[]";
    }
    printMessage("VarExp: " + varStr, level);
    if(exp.index != null) {
      exp.index.accept(this, ++level);
    }
  }

  public void visit( ReturnStatement exp, int level ) {
    printMessage("ReturnExp:", level);
    exp.exp.accept( this, ++level );
  }

  public void visit( FuncDeclaration exp, int level ) {
    indent( level );
    String args = "";
    DeclarationList curr = exp.params;
    
    while( curr != null ) {
      args += ((VarDeclaration) curr.head).type + " " + ((VarDeclaration) curr.head).name;
      curr = curr.tail;
      if(curr != null) {
        args += ", ";
      }
    }
    
    printMessage("Function Declaration: " + exp.type + " " + exp.name + "(" + args + ")", level);
    exp.funcBody.accept( this, ++level );
    
  }

  public void visit( FuncExpression exp, int level ) {
    printMessage("Function Call: " + exp.funcName, level);
    if(exp.args != null)
      exp.args.accept(this, ++level);
  }

  public void visit(VarDeclaration node, int level) {
    String s = "VarDecl: " + node.type + " " + node.name;
    if(node.size > 0) {
      s += "[" + node.size + "]";
    }
    printMessage(s, level);
  }

  @Override
  public void visit(CompoundStatement node, int level) {
    if(node.local_decl != null) {
      node.local_decl.accept(this, level);
    }

    if(node.statements != null) {
      node.statements.accept(this, level);
    }
  }
}
