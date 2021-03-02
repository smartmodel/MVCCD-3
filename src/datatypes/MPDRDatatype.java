package datatypes;

import main.MVCCDElement;

public abstract class MPDRDatatype extends MDRDatatype {
    public MPDRDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
