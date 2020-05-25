package project;

import main.MVCCDElement;
import main.MVCCDManager;

import java.util.ArrayList;

public abstract class ProjectElement extends MVCCDElement {

    private static final long serialVersionUID = 1000;

    //public static final int IDTRANSIENT = -1;

    private int id;
    private boolean transitory = false;



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
            if (parent instanceof Project){
                this.id = ProjectService.getProjectRoot(this).getNextIdElementSequence();
            } else {
                this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
            }
            if ( parent == null) {
                transitory = true;
            }
        }
    }


    public int getId() {
        return id;
    }

    public boolean isTransitory() {
        return transitory;
    }

    public void setTransitory(boolean transitory) {
        this.transitory = transitory;
    }
}
