package mldr;

import mdr.MDRColumn;
import mdr.MDRParameter;
import mdr.MDRParameterFK;
import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public class MLDRParameterFK extends MDRParameterFK implements IMLDRElement {

    public MLDRParameterFK(ProjectElement parent, IMDRParameter columnFK, MDRColumn columnPK ) {

        super(parent, columnFK, columnPK);
    }

}
