package mdr;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDEntity;
import mldr.MLDRColumn;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MDRConstraint extends MDROperation{

    private  static final long serialVersionUID = 1000;

    public MDRConstraint(ProjectElement parent) {
        super(parent);
    }

}
