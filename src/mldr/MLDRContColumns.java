package mldr;

import mcd.MCDElement;
import mdr.MDRContColumns;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRContColumns extends MDRContColumns implements IMLDRElement {

    private  static final long serialVersionUID = 1000;

    public MLDRContColumns(ProjectElement parent, String name) {
        super(parent, name);
    }

     public MCDElement getMcdElementSource() {
        return null;
    }
}
