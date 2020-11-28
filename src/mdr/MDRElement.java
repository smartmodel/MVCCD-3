package mdr;

import m.MElement;
import md.MDElement;
import project.ProjectElement;

public abstract class MDRElement extends MDElement {

    private  static final long serialVersionUID = 1000;

    public MDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MDRElement(ProjectElement parent) {
        super(parent);
    }
}
