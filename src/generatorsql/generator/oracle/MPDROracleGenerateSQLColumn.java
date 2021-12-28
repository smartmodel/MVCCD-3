package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.PreferencesManager;

public class MPDROracleGenerateSQLColumn extends MPDRGenerateSQLColumn {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLColumn(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
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
        return mpdrOracleGenerateSQL;
    }

}
