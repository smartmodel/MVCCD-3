package mpdr.mysql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.MPDRCheckSpecific;
import mpdr.MPDRParameter;
import mpdr.mysql.interfaces.IMPDRMySQLElement;
import project.ProjectElement;

public class MPDRMySQLCheckSpecific extends MPDRCheckSpecific implements IMPDRMySQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, imldrSourceMPDRCConstraintSpecifc);
        return mpdrParameter;

    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRMySQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
