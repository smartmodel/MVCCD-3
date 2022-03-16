package mdr;

import mdr.services.MDRConstraintCustomService;
import project.ProjectElement;

public abstract class MDRConstraintCustom extends MDRConstraint {

    private  static final long serialVersionUID = 1000;

    public MDRConstraintCustom(ProjectElement parent) {
        super(parent);
    }

    public MDRConstraintCustom(ProjectElement parent, int id) {
        super(parent, id);
    }

    public int compareToDefault(MDRConstraintCustom other) {
        return MDRConstraintCustomService.compareToDefault(this,  other);
    }

}

