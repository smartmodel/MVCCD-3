package mpdr.mysql;

import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLParameter extends MPDRParameter implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;
    public MPDRMySQLParameter(IMPDRMySQLElement parent, IMLDRElement mldrElementSource) {
        super((ProjectElement) parent, mldrElementSource);
    }

    public MPDRMySQLParameter(IMPDRMySQLElement parent, IMDRParameter target, IMLDRElement mldrElementSource, int id) {
        super((ProjectElement) parent, target, mldrElementSource, id);
    }
}
