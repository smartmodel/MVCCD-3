package datatypes;

import main.MVCCDElement;
import mpdr.MPDRModel;
import mpdr.mysql.MPDRMySQLModel;

public class MPDRPostgreSQLDatatype extends MPDRDatatype {
    public MPDRPostgreSQLDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRPostgreSQLDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }

    @Override
    protected MPDRModel newMPDRModel() {
        return new MPDRMySQLModel(null, null);
    }


}
