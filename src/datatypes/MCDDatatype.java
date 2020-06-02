package datatypes;

import main.MVCCDElement;

public class MCDDatatype extends MDDatatype {
    public MCDDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MCDDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
