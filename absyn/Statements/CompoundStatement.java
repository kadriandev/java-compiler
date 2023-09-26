package absyn.Statements;

import absyn.AbsynVisitor;
import absyn.Declaration.DeclarationList;

public class CompoundStatement extends Statement {
    
    public DeclarationList local_decl;
    public StatementList statements;

    public CompoundStatement(int row, int col, DeclarationList local_decl, StatementList statements) {
        this.row = row;
        this.col = col;
        this.local_decl = local_decl;
        this.statements = statements;
    }

    public void accept( AbsynVisitor visitor, int level, boolean isAddr ) {
        visitor.visit( this, level, isAddr );
    }
}
