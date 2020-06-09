package m;

import main.MVCCDElement;
import project.ProjectElement;

public abstract class MElement extends ProjectElement {

    public MElement(ProjectElement parent) {
        super(parent);
    }

    public MElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
