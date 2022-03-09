package absyn.Declaration;

import absyn.Absyn;
import absyn.AbsynVisitor;

public class DeclarationList extends Absyn {
    
    public Declaration head;
    public DeclarationList tail;

    public DeclarationList(int row, int col, Declaration head, DeclarationList tail ) {
        this.head = head;
        this.tail = tail;
    }
    
    public void accept( AbsynVisitor visitor, int level ) {
        visitor.visit( this, level );
    }
}
