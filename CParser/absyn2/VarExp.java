package absyn2;

public class VarExp extends Exp {
  public String type, name;

  public VarExp( int row, int col, String type, String name) {
    this.row = row;
    this.col = col;
    this.type = type;
    this.name = name;
  }

  public void accept( AbsynVisitor visitor, int level ) {
    visitor.visit( this, level );
  }
}