package mcd;

import project.ProjectElement;

public class MCDRelations extends MCDElement{

    private static final long serialVersionUID = 1000;
    public MCDRelations(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDRelations(ProjectElement parent) {
        super (parent);
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
