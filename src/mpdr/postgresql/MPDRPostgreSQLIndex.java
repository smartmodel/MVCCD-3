package mpdr.postgresql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRIndex;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLIndex extends MPDRIndex implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLIndex(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLIndex(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, mldrParameter);
        return mpdrParameter;
    }
}
