package absyn.Declaration;

import absyn.Declaration.Declaration;

public class NodeType {

    public String name;
    public Declaration dec;
    public int level;
    
    public NodeType(String name, Declaration dec, int level) {
        this.name = name;
        this.dec = dec;
        this.level = level;
    }

}