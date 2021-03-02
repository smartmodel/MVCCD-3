package mldr;

import mcd.MCDElement;
import mdr.MDRContConstraints;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRContConstraints extends MDRContConstraints implements IMLDRElement {

    private  static final long serialVersionUID = 1000;

    public MLDRContConstraints(ProjectElement parent, String name) {
        super(parent, name);
    }

     public MCDElement getMcdElementSource() {
        return null;
    }
}
