package absyn.Declaration;

import absyn.AbsynVisitor;
import absyn.Statements.CompoundStatement;

public class FuncDeclaration extends Declaration {
    
    public DeclarationList params;    
    public CompoundStatement funcBody;
    
    public FuncDeclaration( int row, int col, String type, String name, DeclarationList params, CompoundStatement funcBody) {
        this.row = row;
        this.col = col;
        this.type = type;
        this.name = name;        
        this.params = params;
        this.funcBody = funcBody;
    }

    public void setFuncBody(CompoundStatement funcBody) {
        this.funcBody = funcBody; 
    }

    public void accept(AbsynVisitor visitor, int level) {
        visitor.visit( this, level );
    }
}
