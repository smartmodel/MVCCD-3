package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDModel;
import preferences.Preferences;
import project.Project;
import project.ProjectElement;
import utilities.files.UtilXML;

public class MCDModels extends MCDElement implements IMCDModel {

    private static final long serialVersionUID = 1000;

    public MCDModels(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDModels(ProjectElement parent) {
        super (parent);
    }


    @Override
    public String getNameTree() {
        return null;
    }
}
