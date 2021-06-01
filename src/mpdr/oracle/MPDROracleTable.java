package mpdr.oracle;

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

public class MPDROracleTable extends MPDRTable {

    private static final long serialVersionUID = 1000;

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource) {
        super(parent, mldrElementSource);
    }

    public MPDROracleTable(ProjectElement parent, IMLDRElement mldrElementSource, int id) {
        super(parent, mldrElementSource, id);
    }

    @Override
    public MPDRColumn createColumn(MLDRColumn mldrColumn) {
        MPDROracleColumn newColumn = MVCCDElementFactory.instance().createMPDROracleColumn(
                getMDRContColumns(), mldrColumn);

        return newColumn;
    }

    @Override
    public MPDRPK createPK(MLDRPK mldrPK) {
        MPDROraclePK newPK = MVCCDElementFactory.instance().createMPDROraclePK(
                getMDRContConstraints(), mldrPK);
        return newPK;
    }

    @Override
    public MDRConstraint createFK(MLDRFK mldrFK) {
        MPDROracleFK newFK = MVCCDElementFactory.instance().createMPDROracleFK(
                getMDRContConstraints(), mldrFK);
        return newFK;
    }
}

