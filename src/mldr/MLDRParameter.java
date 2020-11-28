package mldr;

import md.MDElement;
import mdr.MDRColumn;
import mdr.MDRElement;
import mdr.MDRParameter;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRParameter extends MDRParameter implements IMLDRElement {

    public MLDRParameter(ProjectElement parent, IMDRParameter target) {

        super(parent, target);
    }



}
