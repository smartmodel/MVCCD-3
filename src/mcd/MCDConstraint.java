package mcd;

import project.ProjectElement;

public abstract class MCDConstraint extends MCDOperation{

    public MCDConstraint(ProjectElement parent) {
        super(parent);
    }

    public MCDConstraint(ProjectElement parent, String name) {
        super(parent, name);
    }
}
