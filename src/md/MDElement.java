package md;

import m.MElement;
import main.MVCCDElement;
import mcd.MCDContEndRels;
import project.ProjectElement;

public abstract class MDElement extends MElement {

    public MDElement(ProjectElement parent) {
        super(parent);
    }

    public MDElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
