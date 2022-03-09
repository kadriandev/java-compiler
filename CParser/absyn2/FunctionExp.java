package absyn2;

public class FunctionExp extends Exp {

    public String name;
    public String returnType;
    public ExpList args;
    public ExpList exps;
    
    public FunctionExp( int row, int col, String returnType, String name, ExpList args, ExpList exps) {
        this.row = row;
        this.col = col;
        this.returnType = returnType;
        this.name = name;
        this.args = args;
        this.exps = exps;
    }
    
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
