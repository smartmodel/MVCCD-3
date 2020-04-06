package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDNamePathParent;
import mcd.services.MCDElementService;
import md.MDElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import project.ProjectElement;

import java.util.ArrayList;
import java.util.Collections;

public abstract class MCDElement extends MDElement {


    public MCDElement(ProjectElement parent) {

        super(parent);
    }

    public MCDElement(ProjectElement parent, String name) {

        super(parent, name);
    }

    public String getNamePath(int pathMode) {
        String path = MCDElementService.getPath(this, pathMode);
        if (path !=null){
            return path + getName();
        } else {
            return getName();
        }
    }

    public String getShortNameSmartPath() {
        String path = MCDElementService.getPath(this, MCDElementService.PATHSHORTNAME);
        if (path !=null){
            return path + getShortNameSmart();
        } else {
            return getShortNameSmart();
        }
    }


}
