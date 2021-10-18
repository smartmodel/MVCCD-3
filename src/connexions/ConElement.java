package connexions;

import main.MVCCDElement;

public abstract class ConElement extends MVCCDElement {

    private ConDB db;
    public ConElement(MVCCDElement parent, String name, ConDB db) {
        super(parent, name);
        this.db = db;
    }
}
