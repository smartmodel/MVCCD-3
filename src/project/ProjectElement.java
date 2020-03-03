package project;

import main.MVCCDElement;
import main.MVCCDManager;

public abstract class ProjectElement extends MVCCDElement {

    private int id;

    public ProjectElement(ProjectElement parent) {
        super(parent);
        init();
    }

    public ProjectElement(ProjectElement parent, String name) {
        super(parent, name);
        init();
    }

    private void init() {
        if (this instanceof Project) {
            // Le projet lui-même
            this.id = 0;
        } else {
            this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
        }
    }

    public int getId() {
        return id;
    }
}
