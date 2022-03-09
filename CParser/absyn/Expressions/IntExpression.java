package absyn.Expressions;
import absyn.AbsynVisitor;
import absyn.Statements.ExpressionStatement;

public class IntExpression extends ExpressionStatement {
  public String value;

  public IntExpression( int row, int col, String value ) {
    this.row = row;
    this.col = col;
    this.value = value;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
