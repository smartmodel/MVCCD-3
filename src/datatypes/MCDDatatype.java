package datatypes;

import main.MVCCDElement;
import preferences.Preferences;

public class MCDDatatype extends MDDatatype {
    public MCDDatatype(MVCCDElement parent, String name, boolean abstrait) {
        super(parent, name, abstrait);
    }

    public MCDDatatype(MVCCDElement parent, String name, String lienProg, boolean abstrait) {
        super(parent, name, lienProg, abstrait);
    }

    public boolean isAuthorizedForNID(){
        MCDDatatype token = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_TOKEN_LIENPROG);
        MCDDatatype positiveInteger = MDDatatypeService.getMCDDatatypeByLienProg(Preferences.MCDDATATYPE_POSITIVEINTEGER_LIENPROG);
        return isSelfOrDescendantOf(token) || isSelfOrDescendantOf(positiveInteger);
    }

}
