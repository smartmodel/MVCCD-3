package mpdr.oracle;

import main.MVCCDElementFactory;
import mldr.MLDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRFK;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleFK extends MPDRFK implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleFK(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleFK(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRParameter createParameter(MLDRParameter  mldrParameter) {
        MPDRParameter mpdrParameter = MVCCDElementFactory.instance().createMPDROracleParameter(this, mldrParameter);
        return mpdrParameter;
    }

}
