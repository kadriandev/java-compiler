package absyn.Statements;

import absyn.AbsynVisitor;

public class IfStatement extends Statement {

    public ExpressionStatement test;
    public Statement ifblock;
    public Statement elseblock;

    public IfStatement( int row, int col, ExpressionStatement test, Statement ifblock, Statement elseblock ) {
        this.row = row;
        this.col = col;
        this.test = test;
        this.ifblock = ifblock;
        this.elseblock = elseblock;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
    
}
