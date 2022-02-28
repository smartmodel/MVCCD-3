package mpdr.tapis.oracle;

import mldr.interfaces.IMLDRElement;
import mpdr.tapis.MPDRBoxPackages;
import project.ProjectElement;

public class MPDROracleBoxPackages extends MPDRBoxPackages {

    private  static final long serialVersionUID = 1000;

    public MPDROracleBoxPackages(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDROracleBoxPackages(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleBoxPackages(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }
}
