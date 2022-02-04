package mpdr.tapis;

import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public abstract class MPDRProcedureOrFunction extends MPDRStoredCode {
    private  static final long serialVersionUID = 1000;

    public MPDRProcedureOrFunction(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRProcedureOrFunction(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRProcedureOrFunction(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }
}
