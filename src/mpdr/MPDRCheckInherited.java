package mpdr;

import mldr.interfaces.IMLDRElement;
import mpdr.interfaces.IMPDRConstraintInheritedMLDR;
import project.ProjectElement;

public abstract class MPDRCheckInherited extends MPDRCheck implements IMPDRConstraintInheritedMLDR {
    private  static final long serialVersionUID = 1000;

    public MPDRCheckInherited(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRCheckInherited(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRCheckInherited(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }
}
