package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLIndexColumn;
import mpdr.MPDRColumn;

public class MPDROracleGenerateSQLIndexColumn extends MPDRGenerateSQLIndexColumn {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLIndexColumn(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateIndexColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateIndexColumn(mpdrColumn);
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
