package absyn.Expressions;
import absyn.AbsynVisitor;
import absyn.Statements.ExpressionStatement;

public class OpExpression extends ExpressionStatement {
  public final static int PLUS  = 0;
  public final static int MINUS = 1;
  public final static int TIMES = 2;
  public final static int OVER  = 3;
  public final static int EQ    = 4;
  public final static int NEQ   = 5;
  public final static int LT    = 6;
  public final static int LTE   = 7;
  public final static int GT    = 8;
  public final static int GTE   = 9;

  public ExpressionStatement left;
  public int op;
  public ExpressionStatement right;

  public OpExpression( int row, int col, ExpressionStatement left, int op, ExpressionStatement right ) {
    this.row = row;
    this.col = col;
    this.left = left;
    this.op = op;
    this.right = right;
  }

  public void accept( AbsynVisitor visitor, int level, boolean isAddr ) {
    visitor.visit( this, level, isAddr );
  }

}
