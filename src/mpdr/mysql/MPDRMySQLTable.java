package mpdr.mysql;

import main.MVCCDElementFactory;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRMySQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRMySQLTable(ProjectElement parent, String name, IMLDRElement mldrElementSource) {
        super(parent, name, mldrElementSource);
    }

    public MPDRMySQLTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDRMySQLTable(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRMySQLColumn createColumn(MLDRColumn mldrColumn) {
        MPDRMySQLColumn newColumn = MVCCDElementFactory.instance().createMPDRMySQLColumn(
                getMDRContColumns(),  mldrColumn);

        return newColumn;
    }

    @Override
    public MPDRPK createPK(MLDRPK mldrPK) {
        return null;
    }

    @Override
    public MDRConstraint createFK(MLDRFK mldrFK) {
        return null;
    }

}
