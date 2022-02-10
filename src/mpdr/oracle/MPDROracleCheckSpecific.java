package mpdr.oracle;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCConstraintSpecifc;
import mpdr.MPDRCheckSpecific;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleCheckSpecific extends MPDRCheckSpecific implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleCheckSpecific(ProjectElement parent, IMLDRSourceMPDRCConstraintSpecifc mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleCheckSpecific(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCConstraintSpecifc imldrSourceMPDRCConstraintSpecifc) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, imldrSourceMPDRCConstraintSpecifc);
        return mpdrParameter;
    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
