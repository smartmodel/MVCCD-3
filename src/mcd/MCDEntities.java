package mcd;

import main.MVCCDElement ;
import preferences.Preferences;
import utilities.files.UtilXML;

public class MCDEntities extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDEntities(MVCCDElement mvccdElement, String name) {
        super(mvccdElement,name);
    }

    public MCDEntities(MVCCDElement parent) {
        super (parent);
    }
    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_ENTITIES + " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_ENTITIES);
    }

}
