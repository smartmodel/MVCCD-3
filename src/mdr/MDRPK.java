package mdr;

import md.MDElement;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRColumn;
import mldr.MLDRParameter;
import mldr.services.MLDROperationService;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRPK extends MDRConstraint{

    private  static final long serialVersionUID = 1000;


    public MDRPK(ProjectElement parent) {
        super(parent);
    }

    public abstract MLDRParameter createParameter(IMDRParameter target);
}
