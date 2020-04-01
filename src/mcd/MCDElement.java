package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDNamePathParent;
import mcd.services.MCDElementService;
import md.MDElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

public abstract class MCDElement extends MDElement {


    public MCDElement(ProjectElement parent) {

        super(parent);
    }

    public MCDElement(ProjectElement parent, String name) {

        super(parent, name);
    }

    public String getNamePath() {
        String path = MCDElementService.getPath(this, MCDElementService.PATHNAME);
        if (path !=null){
            return path + getName();
        } else {
            return getName();
        }
    }

    public String getShortNamePath() {
        String path = MCDElementService.getPath(this, MCDElementService.PATHSHORTNAME);
        if (path !=null){
            return path + getShortName();
        } else {
            return getShortName();
        }
    }

}
