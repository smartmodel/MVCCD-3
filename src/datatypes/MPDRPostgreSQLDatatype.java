package datatypes;

import main.MVCCDElement;

public class MPDRPostgreSQLDatatype extends MPDRDatatype {
    public MPDRPostgreSQLDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRPostgreSQLDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


}
