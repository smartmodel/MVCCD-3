package mpdr.oracle;

import mldr.interfaces.IMLDRElement;
import mpdr.MPDRSequence;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;

public class MPDROracleSequence extends MPDRSequence  implements IMPDROracleElement {
    private  static final long serialVersionUID = 1000;

    public MPDROracleSequence(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent,  mldrElementSource, id);
    }

    public MPDROracleSequence(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDROracleSequence(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent,  mldrElementSource);
    }
}
