package mpdr.postgresql;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.MPDRCheckSpecific;
import mpdr.MPDRParameter;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLCheckSpecific extends MPDRCheckSpecific implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, imldrSourceMPDRCConstraintSpecifc);
        return mpdrParameter;
    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDRPostgreSQLParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
