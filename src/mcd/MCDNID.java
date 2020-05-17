package mcd;

import project.ProjectElement;

public class MCDNID extends MCDUnicity {

    public MCDNID(ProjectElement parent) {
        super(parent);
    }

    public MCDNID(ProjectElement parent, String name) {
        super(parent, name);
    }



    @Override
    public String getNameTree() {
        return null;
    }

}
