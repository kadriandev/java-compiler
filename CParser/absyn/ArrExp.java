package absyn;

public class ArrExp extends VarExp {
    public Exp index;

    public ArrExp(int row, int col, String type, String name, Exp index) {
        super(row, col, type, name);
        this.index = index;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
      }
}
