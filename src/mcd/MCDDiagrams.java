package mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import preferences.Preferences;
import utilities.files.UtilXML;

public class MCDDiagrams extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDDiagrams(MVCCDElement mvccdElement, String name) {
        super(mvccdElement,name);
    }

    public MCDDiagrams(MVCCDElement parent) {
        super (parent);
    }

    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_DIAGRAMS + " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_DIAGRAMS);
    }


}
