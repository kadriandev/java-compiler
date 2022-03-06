package absyn;

public class FunctionCall extends Exp {
    
    public String funcName;
    public String[] params;

    public FunctionCall(int row, int column, String funcName, String[] params) {
        this.funcName = funcName;
    }   

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
