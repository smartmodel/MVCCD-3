package mldr;

import exceptions.CodeApplException;
import mcd.MCDElement;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDElementConvert;
import mcd.services.MCDElementService;
import md.MDElement;
import mldr.interfaces.IMLDRModel;
import mldr.services.MLDRElementService;
import project.ProjectElement;

import java.util.ArrayList;

public abstract class MLDRElement extends MDElement {

    private static final long serialVersionUID = 1000;

    public MLDRElement(ProjectElement parent) {
        super(parent);
    }

    public MLDRElement(ProjectElement parent, int id) {
        super(parent, id);
    }

    public MLDRElement(ProjectElement parent, String name) {
        super(parent, name);
    }

}
