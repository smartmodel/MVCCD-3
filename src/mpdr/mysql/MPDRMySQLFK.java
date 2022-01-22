package mpdr.mysql;

import main.MVCCDElementFactory;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRFK;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLFK extends MPDRFK implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLFK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLFK(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MLDRParameter createParameter(IMDRParameter target) {
        return null;
    }

    @Override
    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
