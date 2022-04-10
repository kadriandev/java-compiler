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
    
    static int emitLoc = 0;
    static int highEmitLoc = 0;

    private int mainEntry = 0, globalOffset = 0;
    private FileOutputStream code;

    public CodeGenerator(Absyn result, FileOutputStream out) {
        this.code = out;

        // Generate Prelude
        this.emitComment("generate prelude");
        this.emitRM("LD", gp, 0, ac, "load gp with maxaddr");
        this.emitRM("LDA", fp, 0, gp, "copy gp to fp");
        this.emitRM("ST", ac, 0, ac, "clear content at loc 0");

        int savedLoc = this.emitSkip(1);

        // Generate I/O routines
        this.emitComment("generate i/o routines");
        this.emitComment("code for input routine");
        this.emitRM("ST", ac, -1, fp, "store return");
        this.emitRO("IN", 0, 0, 0, "input");
        this.emitRM("LD", pc, -1, 5, "return to caller");

        this.emitComment("code foe output routine");
        this.emitRM("ST", ac, -1, fp, "store return");
        this.emitRM("LD", ac, -2, fp, "load output value");
        this.emitRO("OUT", 0, 0, 0, "output");
        this.emitRM("LD", pc, -1, 5, "return to caller");

        int savedLoc2 = this.emitSkip(0);
        this.emitBackup(savedLoc);
        this.emitRM_Abs("LDA", pc, savedLoc2, "");
        this.emitRestore();

        // Generate Code From Syntax Tree
        this.emitComment("generate code");
        result.accept(this, 0, false);

        // Generate Finale
        this.emitComment("generate finale");
        this.emitRM("ST", fp, -1, fp, "push ofp");
        this.emitRM("LDA", fp, globalOffset, fp, "push frame");
        this.emitRM("LDA", ac, 1, pc, "load ac with ret ptr");
        this.emitRM_Abs("LDA", pc, mainEntry, "jump to main loc");
        this.emitRM("LD", fp, 0, fp, "pop frame");
        this.emitRO("HALT", 0, 0, 0, "halt program");
    }

    public void visit(DeclarationList expList, int level, boolean isAddr) {
        
        while (expList != null && expList.head != null) {
            expList.head.accept(this, level, false);
            expList = expList.tail;
        }
    }

    public void visit(VarDeclaration node, int offset, boolean isAddr) {
        node.offset = offset;
        if(isAddr) {
            node.nestLevel = 1;
        }else {
            node.nestLevel = 0;
        }
    }

    public void visit(FuncDeclaration exp, int offset, boolean isAddr) {
        int savedLoc = emitSkip(1);

        if(exp.name.equals("main")) {
            mainEntry = emitLoc;
        }

        int ofs = -2;
        DeclarationList dl = exp.params;
        while (dl != null && dl.head != null) {
            dl.head.accept(this, ofs--, true);
            dl = dl.tail;
        }
        
        exp.funcBody.accept(this, offset, false);
        
        int savedLoc2 = emitSkip(0);
        emitBackup(savedLoc);
        emitRM_Abs("LDA", pc, savedLoc2, "jump around fn body");
        emitRestore();
    }

    public void visit(StatementList expList, int level, boolean isAddr) {
        while (expList != null && expList.head != null) {
            expList.head.accept(this, level, false);
            expList = expList.tail;
        }
    }

    public void visit(VarExpression exp, int level, boolean isAddr) {
        if (exp.index != null) {
            exp.index.accept(this, ++level, false);
        }
    }

    public void visit(AssignExpression exp, int level, boolean isAddr) {
        exp.lhs.accept(this, level, true);
        exp.rhs.accept(this, level, false);
    }

    public void visit(IfStatement exp, int level, boolean isAddr) {
        exp.test.accept(this, level, false);
        exp.ifblock.accept(this, ++level, false);
        if (exp.elseblock != null) {
            exp.elseblock.accept(this, ++level, false);
        }
    }

    public void visit(OpExpression exp, int level, boolean isAddr) {
        exp.left.accept(this, level, false);
        exp.right.accept(this, level, false);
    }

    public void visit(WhileStatement exp, int level, boolean isAddr) {
        exp.test.accept(this, level, false);
        exp.exps.accept(this, ++level, false);
    }

    public void visit(ReturnStatement exp, int level, boolean isAddr) {
        exp.exp.accept(this, ++level, false);
    }

    public void visit(FuncExpression exp, int level, boolean isAddr) {
        if (exp.args != null)
            exp.args.accept(this, ++level, false);
    }

    public void visit(CompoundStatement node, int level, boolean isAddr) {
        if (node.local_decl != null) {
            node.local_decl.accept(this, level, false);
        }

        if (node.statements != null) {
            node.statements.accept(this, level, false);
        }
    }

    public void visit(IntExpression exp, int level, boolean isAddr) { }

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
        String str = emitLoc + ":" + op + " " + r + ", " + (a - (emitLoc + 1)) + "(" + pc + ")";
        str += "\t" + c;
        this.printLine(str);
        ++emitLoc;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
    }

    private void emitRO(String op, int r, int s, int t, String c) {
        String str = emitLoc + ":" + op + " " + r + ", " + s + ", " + t;
        str += "\t" + c;
        this.printLine(str);
        ++emitLoc;
        if (highEmitLoc < emitLoc)
            highEmitLoc = emitLoc;
    }

    private void emitRM(String op, int r, int d, int s, String c) {
        String str = emitLoc + ":" + op + " " + r + ", " + d + "(" + s + ")";
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
