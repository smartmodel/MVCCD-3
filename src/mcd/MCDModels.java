package mcd;

import main.MVCCDElement;
import preferences.Preferences;
import project.Project;
import utilities.files.UtilXML;

public class MCDModels extends MCDElement {

    private static final long serialVersionUID = 1000;

    public MCDModels(Project project, String name) {
        super(project, name);
    }

    public MCDModels(MVCCDElement parent) {
        super (parent);
    }
    @Override
    public String baliseXMLBegin() {
        String richBalise = Preferences.XML_BALISE_MODELS + " " +
                UtilXML.attributName(getName());
        return  UtilXML.baliseBegin (richBalise);

    }

    @Override
    public String baliseXMLEnd() {
        return UtilXML.baliseEnd(Preferences.XML_BALISE_MODELS);
    }

}
