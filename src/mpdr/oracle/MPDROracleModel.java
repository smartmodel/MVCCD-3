package mpdr.oracle;

import datatypes.MPDRDatatype;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.oracle.MPDROracleGenerateSQL;
import main.MVCCDElementFactory;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.oracle.interfaces.IMPDROracleElement;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDROracleDatatype;

public class MPDROracleModel extends MPDRModel implements IMPDROracleElement {

    private  static final long serialVersionUID = 1000;

    public MPDROracleModel(ProjectElement parent, String name) {
        super(parent, name, MPDRDB.ORACLE);
    }


    @Override
    public MPDROracleTable createTable(MLDRTable mldrTable){
        MPDROracleTable newTable = MVCCDElementFactory.instance().createMPDROracleTable(
                getMPDRContTables(), mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDROracleDatatype.fromMLDRDatatype(mldrColumn);
    }


    public String treatGenerate() {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDROracleGenerateSQL(this);
        return mpdrGenerateSQL.generate();
    }


}
