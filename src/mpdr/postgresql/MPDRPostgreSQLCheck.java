package mpdr.postgresql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.MPDRCheck;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLCheck extends MPDRCheck implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLCheck(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLCheck(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCheck imldrSourceMPDRCheck) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, imldrSourceMPDRCheck);
        return mpdrParameter;
    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
