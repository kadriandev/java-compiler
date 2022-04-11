import absyn.*;
import absyn.Declaration.*;
import absyn.Expressions.*;
import absyn.Statements.*;

import java.io.FileOutputStream;

public class CodeGenerator implements AbsynVisitor {

    private static final int ac = 0;    // Aritmastic 1
    private static final int ac1 = 1;   // Arithmatic 2
    private static final int fp = 5;    // Frame Pointer
    private static final int gp = 6;    // 
    private static final int pc = 7;    // PC Counter
    
    private static final int ofpFO = 0;
    private static final int retFO = -1;
    private static final int initFO = -2;



    static int emitLoc = 0;
    static int highEmitLoc = 0;

    private int mainEntry = 0, globalOffset = 0, frameOffset = 0;
    private FileOutputStream code;

    public CodeGenerator(Absyn result, FileOutputStream out) {
        this.code = out;
        frameOffset = initFO;
        // Generate Prelude
        this.emitComment("Standard prelude");
        this.emitRM("LD", gp, 0, ac, "load gp with maxaddr");
        this.emitRM("LDA", fp, 0, gp, "copy gp to fp");
        this.emitRM("ST", ac, 0, ac, "clear content at loc 0");

        int savedLoc = this.emitSkip(1);

        // Generate I/O routines
        this.emitComment("Jump around i/o routines here");
        this.emitComment("code for input routine");
        this.emitRM("ST", ac, -1, fp, "store return"); // 
        this.emitRO("IN", 0, 0, 0, "input");
        this.emitRM("LD", pc, -1, 5, "return to caller");

        this.emitComment("code for output routine");
        this.emitRM("ST", ac, -1, fp, "store return");
        this.emitRM("LD", ac, -2, fp, "load output value");
        this.emitRO("OUT", 0, 0, 0, "output");
        this.emitRM("LD", pc, -1, 5, "return to caller");

        int savedLoc2 = this.emitSkip(0);
        this.emitBackup(savedLoc);
        this.emitRM_Abs("LDA", pc, savedLoc2, "jump around i/o code");
        this.emitComment("End of standard prelude");
        this.emitRestore();

        // Generate Code From Syntax Tree
        result.accept(this, 0, false);

        // Generate Finale
        this.emitComment("generate finale");
        this.emitRM("ST", fp, -1, fp, "push ofp");
        this.emitRM("LDA", fp, globalOffset, fp, "push frame");
        this.emitRM("LDA", ac, 1, pc, "load ac with ret ptr");
        this.emitRM_Abs("LDA", pc, mainEntry, "jump to main loc");
        this.emitRM("LD", fp, 0, fp, "pop frame");
        this.emitComment("End of execution.");
        this.emitRO("HALT", 0, 0, 0, "halt program");
    }

    public void visit(DeclarationList expList, int offset, boolean isAddr) {
        
        while (expList != null && expList.head != null) {
            expList.head.accept(this, offset--, isAddr);
            expList = expList.tail;
        }
    }

    public void visit(VarDeclaration node, int offset, boolean isAddr) {
        
        node.offset = offset;
        if(isAddr) {
            frameOffset -= 1;
            this.emitComment("processing local variable: " + node.name);
            node.nestLevel = 1;
        }else {
            this.emitComment("processing global variable: " + node.name);
            node.nestLevel = 0;
        }
        
    }

    public void visit(FuncDeclaration exp, int offset, boolean isAddr) {
        this.emitComment("processing function: " + exp.name);

        int savedLoc = emitSkip(1);

        this.emitComment ("jump around function body here");

        if(exp.name.equals("main")) {
            mainEntry = emitLoc - 1;
        }

        this.emitRM("ST", ac, -1, fp, "store return");

        int ofs = -2;
        DeclarationList dl = exp.params;
        while (dl != null && dl.head != null) {
            dl.head.accept(this, ofs--, true);
            dl = dl.tail;
        }
        

        exp.funcBody.accept(this, ofs, false); // func body is a compound statement

        this.emitRM("LD", pc, -1, fp, "return back to caller");
        
        int savedLoc2 = emitSkip(0);
        emitBackup(savedLoc);
        emitRM_Abs("LDA", pc, savedLoc2, "jump around fn body");
        this.emitComment ("<- fundecl");
        emitRestore();
    }

    public void visit(StatementList expList, int offset, boolean isAddr) {
        while (expList != null && expList.head != null) {
            expList.head.accept(this, offset, false);
            expList = expList.tail;
        }
    }

    public void visit(VarExpression exp, int offset, boolean isAddr) {
        
        
        this.emitComment ("-> id");
        if(isAddr) {
            this.emitComment ("looking up id: " + exp.name);
            this.emitRM("LDA", ac, ((VarDeclaration)(exp.dtype)).offset, fp, "load id address");
            this.emitComment ("<- id");
            this.emitRM("ST", ac, --offset, fp, "   op: push left");
        }else {
            this.emitComment ("looking up id: " + exp.name);
            this.emitRM("LD", ac, ((VarDeclaration)(exp.dtype)).offset, fp, "load id value");
            this.emitComment ("<- id");
            this.emitRM("ST", ac1, offset, fp, "op: push left"); 
        }
    }

    public void visit(AssignExpression exp, int offset, boolean isAddr) {
        int tmpOffset = --offset;
        
        
        this.emitComment ("-> op");
        exp.lhs.accept(this, tmpOffset, true);
        exp.rhs.accept(this, --tmpOffset, false);

        this.emitRM("LD", ac1, offset - 1, fp, "op: load left");
        this.emitRM("ST", ac, 0, ac1, "assign: store value");
        
        this.emitComment ("<- op");

        /*this.emitRM("LD", ac, offset - 1, fp, "");
        this.emitRM("LD", ac1, offset - 2, fp, "");
        this.emitRM("ST", ac1, 0, ac, "");
        this.emitRM("ST", ac1, offset, fp, "");
        */
    }

    public void visit(OpExpression exp, int offset, boolean isAddr) {
        int tmpOffset = offset;
        this.emitComment ("-> op");
        exp.left.accept(this, --tmpOffset, false);
        exp.right.accept(this, --tmpOffset, false);

        //this.emitRM("LD", ac, tmpOffset, fp, "");
        //this.emitRM("LD", ac1, --tmpOffset, fp, "");

        if(exp.op == OpExpression.PLUS) {
            this.emitRO("ADD", 0, 0, 1, "");
        }else if(exp.op == OpExpression.MINUS){
            this.emitRO("SUB", ac, ac1, 0, "op -");
        }else if(exp.op == OpExpression.TIMES){
            this.emitRO("MUL", ac, ac1, 0, "op *");
        }else if(exp.op == OpExpression.OVER){
            this.emitRO("DIV", 0, 0, 1, "");
        }else {
            this.emitRO("SUB", 0, 0, 1, "");
        }
        this.emitComment ("<- op");
        //this.emitRM("ST", ac, --offset, fp, "");
    }

    public void visit(IfStatement exp, int offset, boolean isAddr) {
        // Evaluate test and store in ac
        exp.test.accept(this, offset, false);

        int savedLoc1 = this.emitSkip(1);
        exp.ifblock.accept(this, offset, false);

        int savedLoc2 = this.emitSkip(1);
        this.emitSkip(0);
        this.emitBackup(savedLoc1);
        if(exp.test instanceof OpExpression) {
            OpExpression test = (OpExpression)(exp.test);
            if(test.op == OpExpression.EQ)          this.emitRM_Abs("JEQ", ac, savedLoc1, "");    
            else if(test.op == OpExpression.NEQ)    this.emitRM_Abs("JNE", ac, savedLoc1, "");
            else if(test.op == OpExpression.GT)     this.emitRM_Abs("JGT", ac, savedLoc1, "");
            else if(test.op == OpExpression.GTE)    this.emitRM_Abs("JGE", ac, savedLoc1, "");  
            else if(test.op == OpExpression.LT)     this.emitRM_Abs("JLT", ac, savedLoc1, "");  
            else if(test.op == OpExpression.LTE)    this.emitRM_Abs("JLE", ac, savedLoc1, "");
        }else{
            this.emitRM_Abs("JGT", ac, savedLoc1, "");
        }
        this.emitRestore();

        if (exp.elseblock != null) {
            exp.elseblock.accept(this, offset, false);
        }

        int savedLoc2b = this.emitSkip(0);
        this.emitBackup(savedLoc2);
        this.emitRM_Abs("LDA", pc, savedLoc2b, "");
        this.emitRestore();
    }

    public void visit(WhileStatement exp, int level, boolean isAddr) {
        
        this.emitComment ("-> while");
        
        this.emitComment ("while: jump after body comes back here");
        int savedLocTest = this.emitSkip(0);
        this.emitComment ("-> op");
        exp.test.accept(this, level, false); // must perform an assembly test
        
        this.emitComment ("<- op");
        int savedLocBody = this.emitSkip(1);
        this.emitComment ("while: jump to end belongs here");
        exp.exps.accept(this, level, false);
        
        this.emitRM_Abs("LDA", pc, savedLocTest, "while: absolute jmp to test");

        this.emitBackup(savedLocTest);
        if(exp.test instanceof OpExpression) {
            OpExpression test = (OpExpression)(exp.test);
            if(test.op == OpExpression.EQ)          this.emitRM_Abs("JEQ", ac, savedLocBody, "while: jmp to end");    
            else if(test.op == OpExpression.NEQ)    this.emitRM_Abs("JNE", ac, savedLocBody, "while: ");
            else if(test.op == OpExpression.GT)     this.emitRM_Abs("JGT", ac, savedLocBody, "while: ");
            else if(test.op == OpExpression.GTE)    this.emitRM_Abs("JGE", ac, savedLocBody, "while: ");  
            else if(test.op == OpExpression.LT)     this.emitRM_Abs("JLT", ac, savedLocBody, "while: ");  
            else if(test.op == OpExpression.LTE)    this.emitRM_Abs("JLE", ac, savedLocBody, "while: ");
        }else{
            this.emitRM_Abs("JGT", ac, savedLocBody, "");
        }
        
        this.emitComment ("<- while");
        this.emitRestore();
    }

    public void visit(ReturnStatement exp, int level, boolean isAddr) {
        exp.exp.accept(this, ++level, false);
    }

    public void visit(FuncExpression exp, int offset, boolean isAddr) {
        this.emitComment ("-> call of function: " + exp.funcName);
        int tmpOffset = 0;
        StatementList args = exp.args;
        if (args != null) {
            while (args != null && args.head != null) {
                args.head.accept(this, tmpOffset--, false);
                this.emitRM("ST", ac, frameOffset + initFO + tmpOffset, fp, "");
                tmpOffset--;
                args = args.tail;
            }
        }

        frameOffset--;
        
        //NUMBERS ARE A BIT OFF HERE COMPARED TO THE OUTPUT PROVIDED
        this.emitRM("ST", fp, frameOffset + ofpFO, fp, "    push ofp");
        this.emitRM("LDA", fp, frameOffset, fp, "   push frame");
        this.emitRM("LDA", ac, 1, pc, "     load ac with ret ptr");

        if(exp.funcName.equals("output")) {
            this.emitRM_Abs("LDA", pc, 7, "jump to fn body");
            //handle the output function here
            // Get input
            // Store input
        }else if(exp.funcName.equals("input")) {
            //handle the output function here
            // Load input
            // 
            this.emitRM_Abs("LDA", pc, 4, "jump to fn body");
        }else {
            this.emitRM_Abs("LDA", pc, ((FuncDeclaration)(exp.dtype)).funaddr, "jump to fn body");
        }
        
        this.emitRM("LD", fp, ofpFO, fp, "  pop frame");
        this.emitComment ("<- call");
            
    }

    public void visit(CompoundStatement node, int offset, boolean isAddr) {

        this.emitComment ("-> compound statement");
        
        if (node.local_decl != null) {
            node.local_decl.accept(this, offset, true);
        }

        if (node.statements != null) {
            node.statements.accept(this, offset, false);
        }

        this.emitComment ("<- compound statement");
    }

    public void visit(IntExpression exp, int offset, boolean isAddr) { 
        this.emitComment ("-> constant");
        this.emitRM("LDC", ac, Integer.parseInt(exp.value), ac, "load const");
        this.emitComment ("<- constant");
        //this.emitRM("LD", ac1, --offset, fp, "op: load left");
        //this.emitRM("ST", ac, --offset, fp, "assign: store value");
    }

    // EMIT FUNCTIONS

    private void printLine(String s) {
        try {
            s += "\n";
            if(this.code != null)
                this.code.write(s.getBytes());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private int emitSkip(int distance) {
        int i = emitLoc;
        emitLoc += distance;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
        return i;
    }

    private void emitBackup(int loc) {
        if (loc > highEmitLoc)
            emitComment("BUG in emitBackup");
        emitLoc = loc;
    }

    private void emitRestore() {
        emitLoc = highEmitLoc;
    }

    private void emitRM_Abs(String op, int r, int a, String c) {
        String str = emitLoc + ":     " + op + " " + r + ", " + (a - (emitLoc + 1)) + "(" + pc + ")";
        str += "\t" + c;
        this.printLine(str);
        ++emitLoc;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
    }

    private void emitRO(String op, int r, int s, int t, String c) {
        String str = emitLoc + ":     " + op + " " + r + ", " + s + ", " + t;
        str += "\t" + c;
        this.printLine(str);
        ++emitLoc;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
    }

    private void emitRM(String op, int r, int d, int s, String c) {
        String str = emitLoc + ":     " + op + " " + r + ", " + d + "(" + s + ")";
        str += "\t" + c;
        this.printLine(str);
        ++emitLoc;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
    }

    private void emitComment(String s) {
        String str = "*\t" + s;
        this.printLine(str);
    }

}
