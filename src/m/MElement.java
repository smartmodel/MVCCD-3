package m;

import main.MVCCDElement;
import project.ProjectElement;

public abstract class MElement extends ProjectElement {

    private static final long serialVersionUID = 1000;

    public MElement(ProjectElement parent) {
        super(parent);
    }

    public MElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
