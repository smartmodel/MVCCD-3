package mcd;

import main.MVCCDElement;
import md.MDElement;
import project.ProjectElement;

public abstract class MCDElement extends MDElement {

    public MCDElement(ProjectElement parent) {

        super(parent);
    }

    public MCDElement(ProjectElement parent, String name) {

        super(parent, name);
    }
}
