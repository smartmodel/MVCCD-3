package mcd;

import main.MVCCDElement;
import mcd.interfaces.IMPathOnlyRepositoryTree;
import project.ProjectElement;

import java.util.ArrayList;

public class MCDContRelations extends MCDElement implements IMPathOnlyRepositoryTree {

    private static final long serialVersionUID = 1000;
    public MCDContRelations(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDContRelations(ProjectElement parent) {
        super (parent);
    }

    /*
    public static MCDContRelations getMCDContRelationsByNamePath(int pathMode, String namePath){
        for (MCDContRelations mcdContRelations : ProjectService.getMCDContRelations()){
            if (mcdContRelations.getNamePath(pathMode).equals(namePath)){
                return mcdContRelations;

            }
        }
        return null;
    }

     */


    //TODO-0 PAS A surcharger pour prendre nameTreePath
    public ArrayList<? extends MVCCDElement> getChildsSortDefault() {
        return getChildsSortName();
    }

}
