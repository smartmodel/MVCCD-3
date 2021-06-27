package mldr;

import main.MVCCDElement;
import mcd.MCDElement;
import mdr.MDRColumn;
import mdr.MDRContColumns;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

import java.util.ArrayList;

public class MLDRContColumns extends MDRContColumns implements IMLDRElement {

    private static final long serialVersionUID = 1000;

    public MLDRContColumns(ProjectElement parent, String name) {
        super(parent, name);
    }

    public MCDElement getMcdElementSource() {
        return null;
    }
}

