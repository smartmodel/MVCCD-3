package mpdr.postgresql;

import datatypes.MPDRDatatype;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.postgresql.MPDRPostgreSQLGenerateSQL;
import main.MVCCDElementFactory;
import mldr.MLDRColumn;
import mldr.MLDRTable;
import mpdr.MPDRDB;
import mpdr.MPDRModel;
import mpdr.postgresql.intefaces.IMPDRPostgreSQLElement;
import preferences.Preferences;
import preferences.PreferencesManager;
import project.ProjectElement;
import transform.mldrtompdr.MLDRTransformToMPDRPostgreSQLDatatype;

public class MPDRPostgreSQLModel extends MPDRModel implements IMPDRPostgreSQLElement {

    private  static final long serialVersionUID = 1000;

    public MPDRPostgreSQLModel(ProjectElement parent, String name) {

        super(parent, name, MPDRDB.POSTGRESQL);
    }



    @Override
    public MPDRPostgreSQLTable createTable(MLDRTable mldrTable){
        MPDRPostgreSQLTable newTable = MVCCDElementFactory.instance().createMPDRPostgreSQLTable(
                getMPDRContTables(), mldrTable);

        return newTable;
    }

    @Override
    public MPDRDatatype fromMLDRDatatype(MLDRColumn mldrColumn) {
        return MLDRTransformToMPDRPostgreSQLDatatype.fromMLDRDatatype(mldrColumn);
    }


    public String treatGenerate() {
        MPDRGenerateSQL mpdrGenerateSQL = new MPDRPostgreSQLGenerateSQL(this);
        return mpdrGenerateSQL.generate();
    }

    @Override
    public Boolean getMPDR_TAPIs() {
        Preferences preferences = PreferencesManager.instance().preferences();
        return preferences.getMPDRPOSTGRESQL_TAPIS();

    }

    @Override
    public String getNewRecordWord() {
        return Preferences.MPDR_POSTGRESQL_NEW_RECORD_WORD;
    }



}
