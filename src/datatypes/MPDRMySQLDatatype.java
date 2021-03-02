package datatypes;

import main.MVCCDElement;

public class MPDRMySQLDatatype extends MPDRDatatype {

    public MPDRMySQLDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRMySQLDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }
}
