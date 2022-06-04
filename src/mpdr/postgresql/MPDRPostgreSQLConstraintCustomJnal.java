package mpdr.postgresql;

import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLConstraintCustomJnal extends MPDRConstraintCustomJnal
        implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLConstraintCustomJnal(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        return null;
    }

}
