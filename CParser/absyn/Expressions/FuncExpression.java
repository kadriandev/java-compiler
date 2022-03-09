package absyn.Expressions;

import absyn.AbsynVisitor;
import absyn.Statements.ExpressionStatement;
import absyn.Statements.StatementList;

public class FuncExpression extends ExpressionStatement {
    
    public String funcName;
    public StatementList args;

    public FuncExpression(int row, int col, String funcName, StatementList args) {
        this.row = row;
        this.col = col;
        this.funcName = funcName;
        this.args = args;
    }   

    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
