package mldr;

import mcd.MCDElement;
import mdr.MDRContTables;
import mdr.MDRTable;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRContTables extends MDRContTables implements IMLDRElement {

    private  static final long serialVersionUID = 1000;
    public MLDRContTables(ProjectElement parent, String name) {
        super(parent, name);
    }

}
