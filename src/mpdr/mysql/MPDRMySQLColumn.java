package mpdr.mysql;

import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import project.ProjectElement;

public class MPDRMySQLColumn extends MPDRColumn {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLColumn(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }


}
