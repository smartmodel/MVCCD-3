package mpdr.oracle;

import main.MVCCDElementFactory;
import mdr.interfaces.IMDRParameter;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRFK;
import mpdr.MPDRPK;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleFK extends MPDRFK implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleFK(ProjectElement parent, IMLDRElement mldrElementSource) {
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
