package mdr;

import mdr.interfaces.IMDRParameter;
import project.ProjectElement;

public abstract class MDRParameterFK extends MDRParameter {

    private  static final long serialVersionUID = 1000;

    private MDRColumn columnPK = null;

    public MDRParameterFK(ProjectElement parent, IMDRParameter target, MDRColumn columnPK) {

        super(parent, target);
        this.columnPK= columnPK;
    }


    public MDRColumn getColumnPK() {
        return columnPK;
    }
}
