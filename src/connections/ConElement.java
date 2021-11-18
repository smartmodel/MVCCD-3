package connections;

import application.ApplElement;
import main.MVCCDElement;

public abstract class ConElement extends ApplElement {

    private ConDB conDB;
    protected String lienProg = null;

    public ConElement(MVCCDElement parent, String name, ConDB conDB) {
        super(parent, name);
        this.conDB = conDB;
    }

    public ConElement(MVCCDElement parent, ConDB conDB) {
        super(parent);
        this.conDB = conDB;
    }

    public ConDB getConDB() {
        return conDB;
    }


    public String getLienProg() {
        return lienProg;
    }
}
