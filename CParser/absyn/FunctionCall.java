package absyn;

public class FunctionCall extends Exp {
    
    public String funcName;
    public ExpList params;

    public FunctionCall(int row, int column, String funcName, ExpList params) {
        this.funcName = funcName;
        this.params = params;
    }   

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
