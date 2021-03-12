package mpdr.oracle;

import main.MVCCDElementFactory;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRPK;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROraclePK extends MPDRPK implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROraclePK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }


    @Override
    public MLDRParameter createParameter(IMDRParameter target) {
        return null;
    }

    @Override
    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
