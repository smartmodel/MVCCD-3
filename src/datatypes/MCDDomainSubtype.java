package datatypes;

import main.MVCCDElement;

public class MCDDomainSubtype extends MCDDomain {
    public MCDDomainSubtype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MCDDomainSubtype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }
}
