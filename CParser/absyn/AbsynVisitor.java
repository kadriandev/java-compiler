package absyn;

import absyn.Declaration.*;
import absyn.Statements.*;
import absyn.Expressions.*;


public interface AbsynVisitor {
    public void visit( DeclarationList node, int level );
    public void visit( VarDeclaration node, int level );
    public void visit( FuncDeclaration node, int level );
    public void visit( StatementList node, int level );
    public void visit( CompoundStatement node, int level );
    public void visit( IfStatement node, int level );
    public void visit( WhileStatement node, int level );
    public void visit( ReturnStatement node, int level );
    public void visit( VarExpression node, int level );
    public void visit( IntExpression node, int level );
    public void visit( OpExpression node, int level );
    public void visit( FuncExpression node, int level );
    public void visit( AssignExpression node, int level );

}
