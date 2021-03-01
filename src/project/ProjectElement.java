package project;

import main.MVCCDElement;
import main.MVCCDManager;

import java.util.ArrayList;

public abstract class ProjectElement extends MVCCDElement {

    private static final long serialVersionUID = 1000;

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
            //TODO-1 A priori ok (A voir)
            try {
                this.id = ProjectService.getProjectRoot(this).getNextIdElementSequence();
            } catch(Exception e){
                this.id = MVCCDManager.instance().getProject().getNextIdElementSequence();
            }
            if ( parent == null) {
                transitory = true;
            }
        }
    }

    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //IdProjectElement au lieu de Id
    public int getIdProjectElement() {
        return id;
    }


    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //TransitoryProjectElement au lieu de Transitory
    public boolean isTransitoryProjectElement() {
        return transitory;
    }

    //#MAJ 2021-02-14 Problème de nopmmage en Ingénierie inverse avec VP
    //TransitoryProjectElement au lieu de Transitory
    public void setTransitoryProjectElement(boolean transitory) {
        this.transitory = transitory;
    }



}
