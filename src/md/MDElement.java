package md;

import m.MElement;
import project.ProjectElement;

public abstract class MDElement extends MElement {

    public MDElement(ProjectElement parent) {
        super(parent);
    }

    public MDElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
