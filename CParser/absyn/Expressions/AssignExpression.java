package absyn.Expressions;

import absyn.AbsynVisitor;
import absyn.Statements.ExpressionStatement;

public class AssignExpression extends ExpressionStatement {
  public VarExpression lhs;
  public ExpressionStatement rhs;

  public AssignExpression( int row, int col, VarExpression lhs, ExpressionStatement rhs ) {
    this.row = row;
    this.col = col;
    this.lhs = lhs;
    this.rhs = rhs;
  }
  
  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
