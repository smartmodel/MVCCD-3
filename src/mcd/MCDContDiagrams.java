package mcd;

import project.ProjectElement;

public class MCDContDiagrams extends MCDElement {

    private static final long serialVersionUID = 1000;
    public MCDContDiagrams(ProjectElement parent, String name) {
        super(parent,name);
    }

    public MCDContDiagrams(ProjectElement parent) {
        super (parent);
    }


    @Override
    public String getNameTree() {
        return null;
    }

}
