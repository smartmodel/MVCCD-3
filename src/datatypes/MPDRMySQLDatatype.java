package datatypes;

import main.MVCCDElement;
import mpdr.MPDRModel;
import mpdr.postgresql.MPDRPostgreSQLModel;

public class MPDRMySQLDatatype extends MPDRDatatype {

    public MPDRMySQLDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRMySQLDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }

    @Override
    protected MPDRModel newMPDRModel() {
        return new MPDRPostgreSQLModel(null, null);
    }
}
