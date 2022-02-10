package mpdr.oracle;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mldr.interfaces.IMLDRSourceMPDRCheck;
import mpdr.MPDRCheck;
import mpdr.MPDRCheckInherited;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleCheckInherited extends MPDRCheckInherited implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleCheckInherited(ProjectElement parent, IMLDRSourceMPDRCheck mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleCheckInherited(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(IMLDRSourceMPDRCheck imldrSourceMPDRCheck) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, imldrSourceMPDRCheck);
        return mpdrParameter;
    }


    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
