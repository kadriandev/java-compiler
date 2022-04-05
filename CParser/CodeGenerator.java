public class CodeGenerator implements AbsynVisitor {
    int mainEntry, globalOffset;
    
    public void visit(Absyn trees) { // wrapper for post-order traversal
        // generate the prelude
        // generate the i/o routines
        // call the visit method for DecList
        visit(trees, 0, false);
    }

    // implement all visit methods in AbsynVisitor
    public void visit(DecList decs, int offset, Boolean isAddress) { 
    
    }
}
