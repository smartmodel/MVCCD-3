package mcd;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.files.UtilXML;

public class MCDPackages extends MVCCDElement{

    private static final long serialVersionUID = 1000;
    public MCDPackages(MVCCDElement mvccdElement, String name) {
       super(mvccdElement,name);
    }


    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_PACKAGES+ " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_PACKAGES);
    }

}
