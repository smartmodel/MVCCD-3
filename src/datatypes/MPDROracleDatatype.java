package datatypes;

import main.MVCCDElement;
import mpdr.MPDRModel;
import mpdr.oracle.MPDROracleModel;

public class MPDROracleDatatype extends MPDRDatatype {
    public MPDROracleDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDROracleDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }

    @Override
    protected MPDRModel newMPDRModel() {
        return new MPDROracleModel(null, null);
    }
}
