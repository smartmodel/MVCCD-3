package mpdr.oracle;

import md.MDElement;
import mldr.MLDRColumn;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDROracleTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        return null;
    }


    @Override
    public void setMldrElementSource(IMLDRElement imldrElementSource) {

    }
}
