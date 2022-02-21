package datatypes;

import generatorsql.MPDRGenerateSQLUtil;
import main.MVCCDElement;
import mpdr.MPDRModel;

public abstract class MPDRDatatype extends MDRDatatype {
    public MPDRDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MPDRDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }


    public String getName(){
        MPDRModel mpdrModel =  newMPDRModel();
        return MPDRGenerateSQLUtil.caseReservedWords(super.getName(), mpdrModel);
    }

    protected abstract MPDRModel newMPDRModel();

}
