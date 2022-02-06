package mpdr.mysql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.MPDRCheck;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLCheck extends MPDRCheck implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLCheck(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLCheck(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCheck imldrSourceMPDRCheck) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, imldrSourceMPDRCheck);
        return mpdrParameter;

    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
