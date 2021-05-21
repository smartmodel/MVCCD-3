package mpdr.postgresql;

import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import project.ProjectElement;

public class MPDRPostgreSQLColumn extends MPDRColumn {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRPostgreSQLColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


}
