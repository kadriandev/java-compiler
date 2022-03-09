package absyn2;

public class WhileExp extends Exp {
  public ExpList exps;
  public Exp test;

  public WhileExp( int row, int col, ExpList exps, Exp test ) {
    this.row = row;
    this.col = col;
    this.exps = exps;
    this.test = test;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}
