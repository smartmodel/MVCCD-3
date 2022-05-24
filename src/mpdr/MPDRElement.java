package mpdr;

import md.MDElement;
import project.ProjectElement;

public class MPDRElement extends MDElement {

    private static final long serialVersionUID = 1000;

    public MPDRElement(ProjectElement parent) {
        super(parent);
    }

    public MPDRElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MPDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}