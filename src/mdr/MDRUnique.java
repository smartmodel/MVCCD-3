package mdr;

import project.ProjectElement;

public abstract class MDRUnique extends MDRConstraint {

    private  static final long serialVersionUID = 1000;

    private MDRUniqueNature mdrUniqueNature;
    private boolean absolute = false ;
    private boolean frozen = false ;


    public MDRUnique(ProjectElement parent) {
        super(parent);
    }

    public MDRUnique(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MDRUniqueNature getMdrUniqueNature() {
        return mdrUniqueNature;
    }

    public void setMdrUniqueNature(MDRUniqueNature mdrUniqueNature) {
        this.mdrUniqueNature = mdrUniqueNature;
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}
