package absyn;

import absyn.Declaration.*;
import absyn.Statements.*;
import absyn.Expressions.*;


public interface AbsynVisitor {
    public void visit( DeclarationList node, int level, boolean isAddr );
    public void visit( VarDeclaration node, int level, boolean isAddr );
    public void visit( FuncDeclaration node, int level, boolean isAddr );
    public void visit( StatementList node, int level, boolean isAddr );
    public void visit( CompoundStatement node, int level, boolean isAddr );
    public void visit( IfStatement node, int level, boolean isAddr );
    public void visit( WhileStatement node, int level, boolean isAddr );
    public void visit( ReturnStatement node, int level, boolean isAddr );
    public void visit( VarExpression node, int level, boolean isAddr );
    public void visit( IntExpression node, int level, boolean isAddr );
    public void visit( OpExpression node, int level, boolean isAddr );
    public void visit( FuncExpression node, int level, boolean isAddr );
    public void visit( AssignExpression node, int level, boolean isAddr );

}
