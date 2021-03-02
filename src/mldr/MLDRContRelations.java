package mldr;

import mcd.MCDElement;
import mdr.MDRContRelations;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRContRelations extends MDRContRelations implements IMLDRElement {

    private  static final long serialVersionUID = 1000;

    public MLDRContRelations(ProjectElement parent, String name) {
        super(parent, name);
    }

     public MCDElement getMcdElementSource() {
        return null;
    }
}
