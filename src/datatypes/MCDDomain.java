package datatypes;

import main.MVCCDElement;

public abstract class MCDDomain extends MCDDatatype {
    public MCDDomain(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MCDDomain(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }
}
