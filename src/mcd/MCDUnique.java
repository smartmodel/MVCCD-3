package mcd;

import project.ProjectElement;

public class MCDUnique extends MCDUnicity{

    private  static final long serialVersionUID = 1000;


    public MCDUnique(ProjectElement parent) {

        super(parent);
    }
    public MCDUnique(ProjectElement parent, String name) {

        super(parent, name);
    }


    @Override
    public String getNameTree() {
        return null;
    }


}
