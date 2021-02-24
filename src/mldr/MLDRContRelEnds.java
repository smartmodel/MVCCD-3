package mldr;

import mcd.MCDElement;
import mdr.MDRContRelEnds;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRContRelEnds extends MDRContRelEnds implements IMLDRElement {

    private  static final long serialVersionUID = 1000;

    public MLDRContRelEnds(ProjectElement parent, String name) {
        super(parent, name);
    }

     public MCDElement getMcdElementSource() {
        return null;
    }
}
