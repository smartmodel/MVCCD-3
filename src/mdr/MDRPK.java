package mdr;

import md.MDElement;
import mldr.MLDRColumn;
import mldr.services.MLDROperationService;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRPK extends MDRConstraint{

    private  static final long serialVersionUID = 1000;


    public MDRPK(ProjectElement parent) {
        super(parent);
    }


}
