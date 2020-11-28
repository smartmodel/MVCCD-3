package mpdr.mysql;

import md.MDElement;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRMySQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }


}
