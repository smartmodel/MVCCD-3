package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTableColumn;
import mpdr.MPDRColumn;
import mpdr.MPDRDBPK;
import preferences.PreferencesManager;

public class MPDROracleGenerateSQLTableColumn extends MPDRGenerateSQLTableColumn {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLTableColumn(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTableColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateTableColumn(mpdrColumn);
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
