package mcd;

import main.MVCCDElement ;
import mcd.interfaces.IMCDContPackages;
import preferences.Preferences;
import project.ProjectElement;
import project.ProjectService;
import utilities.files.UtilXML;

import java.util.ArrayList;
import java.util.Collections;

public class MCDContEntities extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDContEntities(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDContEntities(ProjectElement parent) {
        super (parent);
    }


    public static MCDContEntities getMCDContEntitiesByNamePath(int pathMode, String namePath){
        for (MCDContEntities mcdContEntities : ProjectService.getMCDContEntities()){
            if (mcdContEntities.getNamePath(pathMode).equals(namePath)){
                return mcdContEntities;
            }
        }
        return null;
    }


    public ArrayList<MCDEntity> getMCDEntities(){
        ArrayList<MCDEntity> resultat = new ArrayList<MCDEntity>();
        for (MVCCDElement mvccdElement: getChilds()){
            resultat.add((MCDEntity) mvccdElement);
        }
        return resultat;
    }

    public ArrayList<MVCCDElement> getChildsRepository() {
        ArrayList<MVCCDElement> childs = getChilds();
        Collections.sort(childs, MVCCDElement::compareToName);


        return childs;
    }
}
