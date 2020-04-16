package mcd;

import main.MVCCDElement ;
import mcd.interfaces.IMCDContPackages;
import preferences.Preferences;
import project.ProjectElement;
import project.ProjectService;
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

    public static MCDContEntities getMCDContEntitiesByNamePath(int pathMode, String namePath){
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)){
            if (mcdElement instanceof MCDContEntities){
                return (MCDContEntities) mcdElement;
            }
        }
        return null;
    }

}
