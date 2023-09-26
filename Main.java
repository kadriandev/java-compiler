/*
  Created by: Fei Song
  File Name: Main.java
  To Build: 
  After the scanner, tiny.flex, and the parser, tiny.cup, have been created.
    javac Main.java
  
  To Run: 
    java -classpath /usr/share/java/cup.jar:. Main gcd.tiny

  where gcd.tiny is an test input file for the tiny language.
*/
   
import java.io.*;

import absyn.*;
   
class Main {
  public final static boolean SHOW_TREE = true;
  static public void main(String argv[]) {    
    /* Start the parser */
    boolean showTree = false;
    boolean showSymbols = false;
    boolean generateCode = false;
    int showSymbolList = 0;
    String fileName = argv[0].split("/")[1];
    String outFile = "output_files/" + fileName.substring(0, fileName.length() - 3);
    FileOutputStream out = null;

    for(String s: argv) {
      if(s.equals("-s")) {
        showSymbolList = 2;
        showSymbols = true;
      }else if(s.equals("-a") && showSymbolList < 1) {
        showSymbolList = 1;
        showTree = true;
      }else if(s.equals("-c")) {
        generateCode = true;
      }
    }

    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Absyn result = (Absyn)(p.parse().value);
      
      
      if (SHOW_TREE && result != null) {
        if(showSymbolList >= 1) {
          out = new FileOutputStream(outFile + ".abs");
        }else{
          out = null;
        }
        ShowTreeVisitor visitor = new ShowTreeVisitor(result, out);
        
        if(out != null)
          out.close();
      }
      
      
      if(showSymbolList == 2) {
        out = new FileOutputStream(outFile + ".sym");
      }else{
        out = null;
      }  
      SemanticAnalyzer analyzer = new SemanticAnalyzer(result, out);

      if(out != null)
        out.close();
      
      if(generateCode) {
        out = new FileOutputStream(outFile + ".tm");
        CodeGenerator generator = new CodeGenerator(result, out);
      }

      if(out != null)
        out.close();

    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}


