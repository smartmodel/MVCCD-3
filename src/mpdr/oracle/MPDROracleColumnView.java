package mpdr.oracle;

import mldr.interfaces.IMLDRElement;
import mpdr.oracle.interfaces.IMPDROracleElement;
import mpdr.tapis.MPDRColumnView;
import project.ProjectElement;

public class MPDROracleColumnView extends MPDRColumnView implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleColumnView(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleColumnView(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }
}
