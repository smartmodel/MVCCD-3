package mpdr.postgresql;

import main.MVCCDElementFactory;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRFK;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLFK extends MPDRFK implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLFK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLFK(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


    @Override
    public MLDRParameter createParameter(IMDRParameter target) {
        return null;
    }

    @Override
    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
