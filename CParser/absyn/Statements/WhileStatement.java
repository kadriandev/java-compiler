package absyn.Statements;

import absyn.AbsynVisitor;

public class WhileStatement extends Statement {
    
    public Statement exps;
    public ExpressionStatement test;

    public WhileStatement( int row, int col, ExpressionStatement test, Statement exps) {
        this.row = row;
        this.col = col;
        this.exps = exps;
        this.test = test;
    }

    public void accept( AbsynVisitor visitor, int level, boolean isAddr ) {
        visitor.visit( this, level, isAddr );
    }

}
