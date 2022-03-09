package absyn.Statements;

import absyn.AbsynVisitor;

public class ReturnStatement extends Statement {
    
    public ExpressionStatement exp;

    public ReturnStatement( int row, int col, ExpressionStatement e ) {
        this.row = row;
        this.col = col;
        this.exp = e;
    }

    @Override
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
