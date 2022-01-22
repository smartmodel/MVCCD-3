package mpdr.postgresql;

import mldr.interfaces.IMLDRElement;
import mpdr.MPDRSequence;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import project.ProjectElement;

public class MPDRPostgreSQLSequence extends MPDRSequence  implements IMPDRPostgreSQLElement {
    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLSequence(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent,  mldrElementSource, id);
    }

    public MPDRPostgreSQLSequence(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRPostgreSQLSequence(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent,  mldrElementSource);
    }
}
