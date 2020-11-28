package mpdr.postgresql;

import mldr.interfaces.IMLDRElement;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRPostgreSQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }
}
