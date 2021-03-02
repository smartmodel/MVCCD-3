package datatypes;

import main.MVCCDElement;

public class MDRDatatype extends MDDatatype {
    public MDRDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MDRDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
