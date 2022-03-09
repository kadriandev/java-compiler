package absyn.Statements;

import absyn.Absyn;
import absyn.AbsynVisitor;

public class StatementList extends Absyn {
    public Statement head;
    public StatementList tail;

    public StatementList(int row, int col, Statement head, StatementList tail ) {
        this.row = row;
        this.col = col;
        this.head = head;
        this.tail = tail;
    }
    
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
