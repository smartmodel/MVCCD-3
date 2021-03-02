package datatypes;

import main.MVCCDElement;

public class MLDRDatatype extends MDRDatatype {
    public MLDRDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MLDRDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
