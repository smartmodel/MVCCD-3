package mpdr.oracle;

import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleConstraintCustomJnal extends MPDRConstraintCustomJnal
        implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;


    public MPDROracleConstraintCustomJnal(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        return null;
    }

}
