package mcd;

import main.MVCCDElement ;
import preferences.Preferences;
import project.ProjectElement;
import utilities.files.UtilXML;

public class MCDContEntities extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDContEntities(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDContEntities(ProjectElement parent) {
        super (parent);
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
