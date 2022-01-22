package mpdr.postgresql;

import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLParameter extends MPDRParameter implements IMPDRPostgreSQLElement{

    private  static final long serialVersionUID = 1000;
    public MPDRPostgreSQLParameter(IMPDRPostgreSQLElement parent, IMLDRElement mldrElementSource) {
        super((ProjectElement) parent, mldrElementSource);
    }

    public MPDRPostgreSQLParameter(IMPDRPostgreSQLElement parent, IMDRParameter target, IMLDRElement mldrElementSource, int id) {
        super((ProjectElement) parent, target, mldrElementSource, id);
    }
}
