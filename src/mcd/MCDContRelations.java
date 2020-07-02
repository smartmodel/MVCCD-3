package mcd;

import project.ProjectElement;
import project.ProjectService;

public class MCDContRelations extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDContRelations(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDContRelations(ProjectElement parent) {
        super (parent);
    }

    public static MCDContRelations getMCDContRelationsByNamePath(int pathMode, String namePath){
        for (MCDContRelations mcdContRelations : ProjectService.getMCDContRelations()){
            if (mcdContRelations.getNamePath(pathMode).equals(namePath)){
                return mcdContRelations;

            }
        }
        return null;
    }


}
