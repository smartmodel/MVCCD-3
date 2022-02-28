package mpdr.tapis;

import mldr.interfaces.IMLDRElement;
import project.ProjectElement;

public abstract class MPDRPackage extends MPDRStoredCode {

    private  static final long serialVersionUID = 1000;
    MPDRPackageType type  ;

    public MPDRPackage(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRPackage(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPackage(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    public MPDRPackageType getType() {
        return type;
    }

    public void setType(MPDRPackageType type) {
        this.type = type;
    }
}
