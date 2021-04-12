package mdr;

import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import project.ProjectElement;

public abstract class MDRPK extends MDRConstraint{

    private  static final long serialVersionUID = 1000;


    public MDRPK(ProjectElement parent) {
        super(parent);
    }

    public MDRPK(ProjectElement parent, int id) {
        super(parent, id);
    }

    public abstract MLDRParameter createParameter(IMDRParameter target);
}
