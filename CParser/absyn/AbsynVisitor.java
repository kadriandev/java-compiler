package absyn;

public interface AbsynVisitor {

  public void visit( ExpList exp, int level );

  public void visit( AssignExp exp, int level );

  public void visit( IfExp exp, int level );

  public void visit( IntExp exp, int level );

  public void visit( OpExp exp, int level );

  public void visit( WhileExp exp, int level );

  public void visit( VarExp exp, int level );

  public void visit( ReturnExp exp, int level );

  public void visit( FunctionExp exp, int level );

}
