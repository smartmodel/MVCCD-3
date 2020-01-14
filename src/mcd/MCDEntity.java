package mcd;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.files.UtilXML;

public class MCDEntity extends MVCCDElement{

    private String shortName ;

    private static final long serialVersionUID = 1000;

    public MCDEntity(MVCCDElement mvccdElement, String name) {
        super(mvccdElement,name);
    }

    public MCDEntity(MVCCDElement parent) {
        super (parent);
    }
    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_ENTITY + " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_ENTITY);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
