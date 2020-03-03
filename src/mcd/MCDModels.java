package mcd;

import main.MVCCDElement;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;
import utilities.files.UtilXML;

public class MCDModels extends MCDElement {

    private static final long serialVersionUID = 1000;

    public MCDModels(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDModels(ProjectElement parent) {
        super (parent);
    }


}
