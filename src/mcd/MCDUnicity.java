package mcd;

import project.ProjectElement;
import stereotypes.Stereotype;

public abstract class MCDUnicity extends MCDConstraint{

    private  static final long serialVersionUID = 1000;

    public MCDUnicity(ProjectElement parent) {
        super(parent);
    }

    public MCDUnicity(ProjectElement parent, String name) {
        super(parent, name);
    }

    public abstract Stereotype getDefaultStereotype();
}
