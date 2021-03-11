package mpdr.postgresql;

import main.MVCCDElementFactory;
import mdr.MDRConstraint;
import mldr.MLDRColumn;
import mldr.MLDRFK;
import mldr.MLDRPK;
import mldr.interfaces.IMLDRElement;
import mpdr.MPDRColumn;
import mpdr.MPDRPK;
import mpdr.MPDRTable;
import project.ProjectElement;

public class MPDRPostgreSQLTable extends MPDRTable {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLTable(ProjectElement parent,  IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        MPDRPostgreSQLColumn newColumn = MVCCDElementFactory.instance().createMPDRPostgreSQLColumn(
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
