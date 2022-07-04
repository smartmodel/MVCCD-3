package mpdr.mysql;

import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRConstraintCustomAudit;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLConstraintCustomAudit extends MPDRConstraintCustomAudit
        implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;


    public MPDRMySQLConstraintCustomAudit(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        return null;
    }

}
