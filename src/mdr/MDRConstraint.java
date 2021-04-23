package mdr;

import mdr.services.MDRConstraintService;
import project.ProjectElement;

public abstract class MDRConstraint extends MDROperation{

    private  static final long serialVersionUID = 1000;

    public MDRConstraint(ProjectElement parent) {
        super(parent);
    }

    public MDRConstraint(ProjectElement parent, int id) {
        super(parent, id);
    }


    public MDRTable getMDRTableAccueil(){
        return (MDRTable) getParent().getParent();
    }

}
