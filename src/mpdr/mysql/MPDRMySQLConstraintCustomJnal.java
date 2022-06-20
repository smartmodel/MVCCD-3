package mpdr.mysql;

import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRConstraintCustomJnal;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLConstraintCustomJnal extends MPDRConstraintCustomJnal
        implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;


    public MPDRMySQLConstraintCustomJnal(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        return null;
    }

}
