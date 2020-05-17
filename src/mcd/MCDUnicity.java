package mcd;

import project.ProjectElement;

public abstract class MCDUnicity extends MCDConstraint{

    public MCDUnicity(ProjectElement parent) {
        super(parent);
    }

    public MCDUnicity(ProjectElement parent, String name) {
        super(parent, name);
    }
}
