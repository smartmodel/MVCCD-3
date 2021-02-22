package mpdr.postgresql;

import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRPostgreSQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLTable(ProjectElement parent,  IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        return null;
    }
}
