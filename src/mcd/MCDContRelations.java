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

    @Override
    public String getNameTree() {
        return null;
    }


    public static MCDContRelations getMCDContRelationsByNamePath(int pathMode, String namePath){
        for (MCDElement mcdElement : ProjectService.getAllMCDElementsByNamePath(pathMode, namePath)){
            if (mcdElement instanceof MCDContRelations){
                return (MCDContRelations) mcdElement;
            }
        }
        return null;
    }

}
