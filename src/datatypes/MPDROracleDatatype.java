package datatypes;

import main.MVCCDElement;

public class MPDROracleDatatype extends MPDRDatatype {
    public MPDROracleDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDROracleDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
