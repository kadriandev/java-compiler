import absyn.*;
import absyn.Declaration.*;
import absyn.Expressions.*;
import absyn.Statements.*;

public class ShowTreeVisitor implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
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
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfStatement exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    exp.test.accept( this, level );
    System.out.println();
    exp.ifblock.accept( this, level );
    
    if (exp.elseblock != null ) {
      indent(--level);
      System.out.println("ElseExp:"); 
      exp.elseblock.accept( this, ++level );
    }
  }

  public void visit( IntExpression exp, int level ) {
    indent( level );
    System.out.println( "IntExp: " + exp.value ); 
  }

  public void visit( OpExpression exp, int level ) {
    indent( level );
    System.out.print( "OpExp:" ); 
    switch( exp.op ) {
      case OpExpression.PLUS:
        System.out.println( " + " );
        break;
      case OpExpression.MINUS:
        System.out.println( " - " );
        break;
      case OpExpression.TIMES:
        System.out.println( " * " );
        break;
      case OpExpression.OVER:
        System.out.println( " / " );
        break;
      case OpExpression.EQ:
        System.out.println( " = " );
        break;
      case OpExpression.LT:
        System.out.println( " < " );
        break;
      case OpExpression.GT:
        System.out.println( " > " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( WhileStatement exp, int level ) {
    indent( level );
    System.out.println( "WhileExp:" );
    level++;
    exp.test.accept( this, level );
    System.out.println();
    exp.exps.accept( this, level );
  }

  public void visit( VarExpression exp, int level ) {
    indent( level );
    String varStr = exp.name;
    if(exp.index != null) {
      varStr += "[]";
    }
    System.out.println( "VarExp: " + varStr );
    if(exp.index != null) {
      exp.index.accept(this, ++level);
    }
  }

  public void visit( ReturnStatement exp, int level ) {
    indent( level );
    System.out.println( "ReturnExp:" );
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
    
    System.out.println( "Function Declaration: " + exp.type + " " + exp.name + "(" + args + ")");
    exp.funcBody.accept( this, ++level );
    
  }

  public void visit( FuncExpression exp, int level ) {
    indent(level);
    System.out.println( "Function Call: " + exp.funcName);
    if(exp.args != null)
      exp.args.accept(this, ++level);
  }

  public void visit(VarDeclaration node, int level) {
    indent(level);
    String s = "VarDecl: " + node.type + " " + node.name;
    if(node.size > 0) {
      s += "[" + node.size + "]";
    }
    System.out.println(s);
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
