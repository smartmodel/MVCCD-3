package project;

import main.MVCCDElement;
import main.MVCCDManager;

import java.util.ArrayList;

public abstract class ProjectElement extends MVCCDElement {

    private static final long serialVersionUID = 1000;

    private int id;

    public ProjectElement(ProjectElement parent) {
        super(parent);
        init(parent);
    }

    public ProjectElement(ProjectElement parent, String name) {
        super(parent, name);
        init(parent);
    }

    private void init(ProjectElement parent) {
        if (this instanceof Project) {
            // Le projet lui-même
            this.id = 0;
        } else {
            this.id = ProjectService.getProjectRoot(this).getNextIdElementSequence();
            /*
            if (MVCCDManager.instance().getProject() != null){
                this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
            } else {
                // Le projet est en cours de création et n'est pas référencé
                ((Project) parent).getNextIdElementSequence();
            }
            if ( parent instanceof Project){
                // Le projet est en cours de création et n'est pas référencé
                ((Project) parent).getNextIdElementSequence();
            } else {
                this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
            }

             */
        }
    }

    public int getId() {
        return id;
    }

}
