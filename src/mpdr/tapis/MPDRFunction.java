package mpdr.tapis;

import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public abstract class MPDRFunction extends MPDRProcedureOrFunction {

    private  static final long serialVersionUID = 1000;
    MPDRFunctionType type  ;

    public MPDRFunction(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRFunction(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRFunction(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    public MPDRFunctionType getType() {
        return type;
    }

    public void setType(MPDRFunctionType type) {
        this.type = type;
    }
}
