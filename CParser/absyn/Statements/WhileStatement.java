package absyn.Statements;

import absyn.AbsynVisitor;

public class WhileStatement extends Statement {
    
    public Statement exps;
    public Statement test;

    public WhileStatement( int row, int col, Statement test, Statement exps) {
        this.row = row;
        this.col = col;
        this.exps = exps;
        this.test = test;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }

}
