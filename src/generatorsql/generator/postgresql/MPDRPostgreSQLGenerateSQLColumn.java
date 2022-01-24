package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.PreferencesManager;

public class MPDRPostgreSQLGenerateSQLColumn extends MPDRGenerateSQLColumn {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLColumn(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateColumn(mpdrColumn);
        return generateSQLCode;
    }

    @Override
    protected boolean pkGenerateIdentity() {
        return  PreferencesManager.instance().preferences().getMPDRORACLE_PK_GENERATE() == MPDRDBPK.IDENTITY;
    }


    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
