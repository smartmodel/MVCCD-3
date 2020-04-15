package mcd;

import project.ProjectElement;

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
}
