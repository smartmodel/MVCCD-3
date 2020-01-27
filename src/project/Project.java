package project;

import main.MVCCDElement;
import main.MVCCDElementSerializable;
import main.MVCCDManager;
import preferences.Preferences;
import utilities.files.UtilXML;

public class Project extends MVCCDElementSerializable {

    private static final long serialVersionUID = 1000;

    public Project(String name) {

        super(null, name);
    }


    public Project(MVCCDElement parent) {

        super(parent);
    }

    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_PROJECT + " " +
                UtilXML.attributName(getName());
        return UtilXML.baliseBegin(richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_PROJECT);
    }

}