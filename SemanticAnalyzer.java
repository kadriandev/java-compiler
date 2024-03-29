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

  public SemanticAnalyzer(Absyn result, FileOutputStream out) {
    this.outFile = out;

    this.printSymbols = out != null;
    printMessage("Entering the global scope:", 0);
    int level = 0;
    result.accept(this, ++level, false);

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
          if(((VarDeclaration)(type.dec)).size != null){
            printMessage(type.name + ": " + type.dec.type + "[" + ((VarDeclaration)(type.dec)).size + "]", 1);
          }else {
            printMessage(type.name + ": " + type.dec.type, 1);
          }
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

  public void visit( DeclarationList expList, int level, boolean isAddr ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level, false );
      expList = expList.tail;
    } 
  }

  public void visit(VarDeclaration node, int level, boolean isAddr) {
    NodeType type = new NodeType(node.name, node, level);
    insert(type);
    if(level > 1)
      printMessage(node.name + ": " + node.type, level);
  }

  public void visit( FuncDeclaration exp, int level, boolean isAddr ) {
    printMessage( "Entering the function scope for " + exp.name + ":", level);
    insert(new NodeType(exp.name, exp, level));
    this.currFunc = exp;
    level++;
    DeclarationList dl = exp.params;
    while(dl != null && dl.head != null) {
      dl.head.accept(this, level, false);
      dl = dl.tail;
    }
    exp.funcBody.accept( this, level, false );
    this.currFunc = null;
    delete(level);
    printMessage("Leaving function scope", --level);
  }

  public void visit( StatementList expList, int level, boolean isAddr ) {
    while( expList != null && expList.head != null) {
      expList.head.accept( this, level, false );
      expList = expList.tail;
    } 
  }

  public void visit( VarExpression exp, int level, boolean isAddr ) {
    String varStr = exp.name;
    NodeType type = lookup(varStr);

    if(type == null) {
      System.err.println("Error: variable does not exist\n");
    }else {
      if(type.dec.type.equals("int") && exp.index != null) {
        System.err.println("Error: Variable type is not subscriptable (line " 
        + (exp.row + 1) + ", col " + exp.index.col + ")");
      }
      exp.dtype = type.dec;
    }

    // Check if index for arrays is an integer type
    if(exp.index != null) {
      exp.index.accept(this, ++level, false);
      if(!exp.index.dtype.type.equals("int")){
        System.err.println("Error: Array index must be of type int (line " 
          + (exp.index.row + 1) + ", col " + exp.index.col + ")");
      }
    }
  }


  public void visit( AssignExpression exp, int level, boolean isAddr ) {
    exp.lhs.accept( this, level, false );
    exp.rhs.accept( this, level, false );
    if(exp.lhs.dtype.type.equals("void") || exp.lhs.dtype.type != exp.rhs.dtype.type) {
      System.err.println("Error: Cannot assign type " + exp.rhs.dtype.type + " to type " + 
        exp.lhs.dtype.type + " (line " + (exp.row + 1) + ", col " + exp.col + ")");
    }
  }

  public void visit( IfStatement exp, int level, boolean isAddr ) {
    exp.test.accept( this, level, false );

    if (exp.test.dtype.type != "int" && exp.test.dtype.type != "boolean") {
      System.err.println("Error: If statement test must be an int or int comparison (line " 
        + (exp.test.row + 1) + ", col " + exp.test.col + ")");
    }

    printMessage("Entering a new block:", level);
    exp.ifblock.accept( this, ++level, false );
    delete(level);
    printMessage("Leaving the block", --level);

    if (exp.elseblock != null ) {
      printMessage("Entering a new block:", level);
      exp.elseblock.accept( this, ++level, false );
      delete(level);
      printMessage("Leaving the block", --level);
    }
  }

  public void visit( IntExpression exp, int level, boolean isAddr ) {
    exp.dtype = new VarDeclaration(exp.row, exp.col, "int", "", null);
  }

  public void visit( OpExpression exp, int level, boolean isAddr ) { 
    exp.left.accept( this, level, false );
    exp.right.accept( this, level, false );

    if(exp.left.dtype.type.equals("int") && exp.right.dtype.type.equals("int") 
      && (exp.op >= 0 && exp.op <= 3)) {
      exp.dtype = new VarDeclaration(exp.row, exp.col, "int", "", null);
    }else if(exp.left.dtype.type.equals("int") && exp.right.dtype.type.equals("int") 
      && exp.op >= 4) {
        exp.dtype = new VarDeclaration(exp.row, exp.col, "boolean", "", null);
    } else {
      System.err.println("Error: Cannot evaluate expression (line " 
        + (exp.row + 1) + ", col " + exp.col + ")");
        exp.dtype = new VarDeclaration(exp.row, exp.col, "error", "", null);
    }
  }

  public void visit( WhileStatement exp, int level, boolean isAddr ) {
    exp.test.accept( this, level, false );

    if (exp.test.dtype.type != "int" && exp.test.dtype.type != "boolean") {
      System.err.println("Error: While statement test must be an int or int comparison (line " 
        + (exp.test.row + 1) + ", col " + exp.test.col + ")");
    }

    printMessage("Entering a new block:", level);
    exp.exps.accept( this, ++level, false );
    delete(level);
    printMessage("Leaving the block", --level);
  }

  public void visit( ReturnStatement exp, int level, boolean isAddr ) {

    if(currFunc == null) {
      System.err.println("Error: Return can only be used inside a function body (line " 
        + (exp.row + 1) + ", col " + exp.col + ")");
    }

    exp.exp.accept( this, ++level, false );
    NodeType func = lookup(currFunc.name);
    if(!exp.exp.dtype.type.equals(func.dec.type)) {
      System.err.println("Error: Return type doesn't match type specified in function declaration (line " 
        + (exp.row + 1) + ", col " + exp.col + ")");
    }
  }

  public void visit( FuncExpression exp, int level, boolean isAddr ) {
    if(exp.args != null)
      exp.args.accept(this, ++level, false);

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

  public void visit(CompoundStatement node, int level, boolean isAddr) {
    if(node.local_decl != null) {
      node.local_decl.accept(this, level, false);
    }

    if(node.statements != null) {
      node.statements.accept(this, level, false);
    }
  }

}