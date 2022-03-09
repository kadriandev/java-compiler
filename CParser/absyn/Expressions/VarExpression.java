package absyn.Expressions;

import absyn.AbsynVisitor;
import absyn.Statements.ExpressionStatement;

public class VarExpression extends ExpressionStatement {

    public String name;
    public ExpressionStatement index;

    public VarExpression( int row, int col, String name, ExpressionStatement index) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.index = index;
    }

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }

}

