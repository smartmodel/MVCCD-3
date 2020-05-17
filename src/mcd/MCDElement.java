package mcd;

import mcd.services.MCDElementService;
import md.MDElement;
import project.ProjectElement;

public abstract class MCDElement extends MDElement {

    private static final long serialVersionUID = 1000;


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
