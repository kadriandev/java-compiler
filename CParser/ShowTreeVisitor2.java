import absyn2.*;

public class ShowTreeVisitor2 implements AbsynVisitor {

  final static int SPACES = 4;

  private void indent( int level ) {
    for( int i = 0; i < level * SPACES; i++ ) System.out.print( " " );
  }

  public void visit( ExpList expList, int level ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( AssignExp exp, int level ) {
    indent( level );
    System.out.println( "AssignExp:" );
    level++;
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );
  }

  public void visit( IfExp exp, int level ) {
    indent( level );
    System.out.println( "IfExp:" );
    level++;
    exp.test.accept( this, level );
    System.out.println();
    exp.thenpart.accept( this, level );
    
    if (exp.elsepart != null ) {
      indent(--level);
      System.out.println("ElseExp:"); 
      exp.elsepart.accept( this, ++level );
    }
  }

  public void visit( IntExp exp, int level ) {
    indent( level );
    System.out.println( "IntExp: " + exp.value ); 
  }

  public void visit( OpExp exp, int level ) {
    indent( level );
    System.out.print( "OpExp:" ); 
    switch( exp.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.TIMES:
        System.out.println( " * " );
        break;
      case OpExp.OVER:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + exp.row + " and column " + exp.col);
    }
    level++;
    exp.left.accept( this, level );
    exp.right.accept( this, level );
  }

  public void visit( WhileExp exp, int level ) {
    indent( level );
    System.out.println( "WhileExp:" );
    level++;
    exp.test.accept( this, level );
    System.out.println();
    exp.exps.accept( this, level );
  }

  public void visit( VarExp exp, int level ) {
    indent( level );
    System.out.println( "VarExp: " + exp.name );
  }

  public void visit( ArrExp exp, int level ) {
    indent( level );
    System.out.println( "ArrExp: " + exp.name + "[]");
    exp.index.accept(this, ++level);
  }

  public void visit( ReturnExp exp, int level ) {
    indent( level );
    System.out.println( "ReturnExp:" );
    exp.exp.accept( this, ++level );
  }

  public void visit( FunctionExp exp, int level ) {
    indent( level );
    String args = "";
    ExpList curr = exp.args;
    
    while( curr != null ) {
      args += ((VarExp) curr.head).type + " " + ((VarExp) curr.head).name;
      curr = curr.tail;
      if(curr != null) {
        args += ", ";
      }
    }
    

    System.out.println( "Function Declaration: " + exp.returnType + " " + exp.name + "(" + args + ")");
    exp.exps.accept( this, ++level );
    
  }

  public void visit( FunctionCall exp, int level ) {
    indent( level );
    System.out.println( "Function Call: " + exp.funcName);
    if(exp.params != null)
      exp.params.accept(this, ++level);
  }



}