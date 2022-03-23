package absyn.Declaration;

import absyn.AbsynVisitor;

public class VarDeclaration extends Declaration {

    public Integer size = 0;
    
    public VarDeclaration(int row, int col, String type, String name, Integer size) {
        this.row = row;
        this.col = col;
        this.name = name;
        this.type = type;

        if(size != null) {
            this.size = size;
        }
    }
    public void accept(AbsynVisitor visitor, int level) {
        visitor.visit( this, level );
    }
}
