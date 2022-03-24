import absyn.*;
import absyn.Declaration.*;
import absyn.Expressions.*;
import absyn.Statements.*;

import java.util.HashMap;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class SemanticAnalyzer implements AbsynVisitor {


  final static int SPACES = 4;
  private FileOutputStream outFile;
  private boolean printSymbols;
  private HashMap<String, ArrayList<NodeType>> map = new HashMap<>();
  private FuncDeclaration currFunc = null;
  private String errors = "";

  public SemanticAnalyzer(Absyn result, FileOutputStream out) {
    this.outFile = out;

    this.printSymbols = out != null;
    printMessage("Entering the global scope:", 0);
    int level = 0;
    result.accept(this, ++level);

    for(ArrayList<NodeType> arr: map.values()) {
      for(NodeType type: arr) {
        if(type.dec instanceof FuncDeclaration) {
          String funcStr = type.name + ": (";
          DeclarationList p = ((FuncDeclaration)(type.dec)).params;
          
          if(p == null) {
            funcStr += "void";
          }
          
          while(p != null && p.head != null) {
            funcStr += p.head.type;
            if(p.tail != null)
              funcStr += ",";
            p = p.tail;
          }
          funcStr += (") -> " + type.dec.type);
          printMessage(funcStr, 1);
        }else if(type.dec instanceof VarDeclaration) {
          printMessage(type.name + ": " + type.dec.type, 1);
        }
      }
    }

    printMessage("Leaving global scope", 0);
  }

  private void printMessage(String message, int level) {
    try{
      if(this.printSymbols) {
        for( int i = 0; i < level * SPACES; i++ ) this.outFile.write(" ".getBytes());
        this.outFile.write((message + "\n").getBytes());
      }
    }catch(IOException e){
      e.printStackTrace();
    }
  }

  /*
    IS THIS A USELESS FUNCTION >?

    0                      
     ---------------------- ) ~   ~
     ---------------------- )           ~   ~
    0                                             ~   ~
                                                          ~
    IS THIS A USELESS FUNCTION >?                       ~~~~~~~
  */
  public void printErrors() {
    System.err.println(this.errors);
  }

  private void insert(NodeType node) {
    if(map.get(node.name) == null) {
      map.put(node.name, new ArrayList<NodeType>());
    }
    map.get(node.name).add(0, node);
  }

  private NodeType lookup(String name) {
    NodeType n = null;
    ArrayList<NodeType> arr = map.get(name);
    
    if(arr == null) {
      return null;
    }

    for(NodeType node: arr) {
      if(node.name.equals(name)) {
        n = node;
        break;
      }
    }
    return n;
  }

  private void delete(int level) {
    for(ArrayList<NodeType> arr: map.values()) {
      Iterator i = arr.iterator();
      while(i.hasNext()) {
        NodeType n = (NodeType) i.next();
        if(n.level == level) {
          i.remove();
        }
      }
    }
  }

  private boolean isInteger(Declaration dtype){
    return true;
  }

  public void visit( DeclarationList expList, int level ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit(VarDeclaration node, int level) {
    NodeType type = new NodeType(node.name, node, level);
    insert(type);
    if(level > 1)
      printMessage(node.name + ": " + node.type, level);
  }

  public void visit( FuncDeclaration exp, int level ) {
    printMessage( "Entering the function scope for " + exp.name + ":", level);
    insert(new NodeType(exp.name, exp, level));
    this.currFunc = exp;
    level++;
    DeclarationList dl = exp.params;
    while(dl != null && dl.head != null) {
      dl.head.accept(this, level);
      dl = dl.tail;
    }
    exp.funcBody.accept( this, level );
    this.currFunc = null;
    delete(level);
    printMessage("Leaving function scope", --level);
  }

  public void visit( StatementList expList, int level ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level );
      expList = expList.tail;
    } 
  }

  public void visit( VarExpression exp, int level ) {
    String varStr = exp.name;
    if(exp.index != null) {
      varStr += "[]";
    }

    NodeType type = lookup(varStr);
    if(type == null) {
      System.err.println("Error: variable does not exist\n");
    }else {
      exp.dtype = type.dec;
    }

    // Check if index for arrays is an integer type
    if(exp.index != null) {
      exp.index.accept(this, ++level);
      if(!exp.index.dtype.type.equals("int")){
        System.err.println("Error: Array index must be of type int (line " 
          + exp.index.row + ", col " + exp.index.col + ")");
      }
    }
  }


  public void visit( AssignExpression exp, int level ) {
    exp.lhs.accept( this, level );
    exp.rhs.accept( this, level );

    if(exp.lhs.dtype.type.equals("void") || exp.lhs.dtype.type != exp.rhs.dtype.type) {
      System.err.println("Error: Cannot assign type " + exp.rhs.dtype.type + " to type " + 
        exp.lhs.dtype.type + " (line " + exp.row + ", col " + exp.col + ")");
    }
  }

  public void visit( IfStatement exp, int level ) {
    exp.test.accept( this, level );

    printMessage("Entering a new block:", level);
    exp.ifblock.accept( this, ++level );
    delete(level);
    printMessage("Leaving the block", --level);

    if (exp.elseblock != null ) {
      printMessage("Entering a new block:", level);
      exp.elseblock.accept( this, ++level );
      delete(level);
      printMessage("Leaving the block", --level);
    }
    if (exp.ifblock.dtype.type != "boolean" || exp.elseblock.dtype.type != "boolean") {
      System.err.println("Error! Test conditions must be of type boolean!");
    }
  }

  public void visit( IntExpression exp, int level ) {
    exp.dtype = new VarDeclaration(exp.row, exp.col, "int", "", null);
  }

  public void visit( OpExpression exp, int level ) { 
    exp.left.accept( this, level );
    exp.right.accept( this, level );

    /* TODO: Check if both left and right side are int types
      IS THIS DONE??
      IS THIS DONE??
    */
    if(exp.left.dtype.type.equals("int") && exp.right.dtype.type.equals("int") 
      && (exp.op >= 0 && exp.op <= 3)) {
      exp.dtype = new VarDeclaration(exp.row, exp.col, "int", "", null);
    }else if(exp.left.dtype.type.equals("int") && exp.right.dtype.type.equals("int") 
      && exp.op >= 4) {
        exp.dtype = new VarDeclaration(exp.row, exp.col, "boolean", "", null);
    } else {
      System.err.println("Error: Cannot evaluate expression (line " 
        + exp.row + ", col " + exp.col + ")");
    }
  }

  public void visit( WhileStatement exp, int level ) {
    exp.test.accept( this, level );
    printMessage("Entering a new block:", level);
    exp.exps.accept( this, ++level );
    delete(level);
    printMessage("Leaving the block", --level);
    if (exp.exps.dtype.type != "boolean" || exp.test.dtype.type != "boolean") {
      System.out.println("Error! Test conditions must be of type boolean!");
    }
  }

  public void visit( ReturnStatement exp, int level ) {

    if(currFunc == null) {
      System.err.println("Error: Return can only be used inside a function body (line " 
        + exp.row + ", col " + exp.col + ")");
    }

    exp.exp.accept( this, ++level );
    NodeType func = lookup(currFunc.name);
    if(!exp.exp.dtype.type.equals(func.dec.type)) {
      System.err.println("Error: Return type doesn't match type specified in function declaration (line " 
        + exp.row + ", col " + exp.col + ")");
    }
  }

  

  public void visit( FuncExpression exp, int level ) {
    
    if(exp.args != null)
      exp.args.accept(this, ++level);

    NodeType n = lookup(exp.funcName);
    // TODO: Check if each arg is the right type

    // Set the type of this expression
    if(n == null && exp.funcName == "input") {
      exp.dtype = new FuncDeclaration(exp.row, exp.col, "int", "input", null, null);
    }else if(n == null && exp.funcName == "output") {
      exp.dtype = new FuncDeclaration(exp.row, exp.col, "void", "output", null, null);
    }else{
      exp.dtype = n.dec;
    }

    
    
  }

  public void visit(CompoundStatement node, int level) {
    
    if(node.local_decl != null) {
      node.local_decl.accept(this, level);
    }

    if(node.statements != null) {
      node.statements.accept(this, level);
    }
  }

}