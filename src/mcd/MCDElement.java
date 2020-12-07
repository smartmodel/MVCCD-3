package mcd;

import main.MVCCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import org.apache.commons.lang.StringUtils;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;

public abstract class MCDElement extends MDElement {

    private static final long serialVersionUID = 1000;

    public MCDElement(ProjectElement parent) {
        super(parent);
    }

    public MCDElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    public String getPath(int pathMode, String separator) {
        return MCDElementService.getPath(this, pathMode, separator);
    }

    public String getShortPath(String separator) {
        return MCDElementService.getPath(this, MCDElementService.PATHSHORTNAME, separator);
    }

    public String getNormalPath(String separator) {
        return MCDElementService.getPath(this, MCDElementService.PATHNAME, separator);
    }



    public String getNamePath(int pathMode) {
        String separator = Preferences.MODEL_NAME_PATH_SEPARATOR;
        String path = getPath( pathMode, separator);
        if (StringUtils.isNotEmpty(path)){
            return path + separator + getName();
        } else {
            return getName();
        }
    }

    public String getShortNameSmartPath() {
        String separator = Preferences.MODEL_NAME_PATH_SEPARATOR;
        String path = getPath( MCDElementService.PATHSHORTNAME, separator);
        if (path !=null){
            return path + separator + getShortNameSmart();
        } else {
            return getShortNameSmart();
        }
    }



    public MCDContRelEnds getMCDContEndRels() {
        for (MVCCDElement mvccdElement : getChilds()){
            if (mvccdElement instanceof MCDContRelEnds) {
                return (MCDContRelEnds) mvccdElement;
            }
        }
        return null;
    }

}
