package absyn2;

public class ReturnExp extends Exp {
  public Exp exp;

  public ReturnExp( int row, int col, Exp e ) {
    this.row = row;
    this.col = col;
    this.exp = e;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}