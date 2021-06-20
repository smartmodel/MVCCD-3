package mdr;

import mdr.interfaces.IMDRParameter;
import project.ProjectElement;
import project.ProjectService;

public abstract class MDRParameterFK extends MDRParameter {

    private  static final long serialVersionUID = 1000;

    //private MDRColumn columnPK = null;
    private int columnPKId;

    public MDRParameterFK(ProjectElement parent, IMDRParameter target, MDRColumn columnPK) {

        super(parent, target);
        this.columnPKId = columnPK.getIdProjectElement();
    }

    public MDRColumn getColumnPK() {
        return (MDRColumn) ProjectService.getProjectElementById(columnPKId);
    }
}
