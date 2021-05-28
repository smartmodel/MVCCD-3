package mpdr.oracle;

import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleParameter extends MPDRParameter {

    public MPDROracleParameter(IMPDROracleElement parent, IMLDRElement mldrElementSource) {
        super((ProjectElement) parent, mldrElementSource);
    }

    public MPDROracleParameter(IMPDROracleElement parent, IMDRParameter target, IMLDRElement mldrElementSource) {
        super((ProjectElement) parent, target, mldrElementSource);
    }
}
