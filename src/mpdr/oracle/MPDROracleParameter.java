package mpdr.oracle;

import mdr.interfaces.IMDRParameter;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRParameter;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleParameter extends MPDRParameter implements IMPDROracleElement{

    private  static final long serialVersionUID = 1000;
    public MPDROracleParameter(IMPDROracleElement parent, IMLDRElement mldrElementSource) {
        super((ProjectElement) parent, mldrElementSource);
    }

    public MPDROracleParameter(IMPDROracleElement parent, IMDRParameter target, IMLDRElement mldrElementSource, int id) {
        super((ProjectElement) parent, target, mldrElementSource, id);
    }
}
