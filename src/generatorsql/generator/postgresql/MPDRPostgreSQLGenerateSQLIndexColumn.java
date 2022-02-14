package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLIndexColumn;
import mpdr.MPDRColumn;

public class MPDRPostgreSQLGenerateSQLIndexColumn extends MPDRGenerateSQLIndexColumn {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;

    public MPDRPostgreSQLGenerateSQLIndexColumn(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateIndexColumn(MPDRColumn mpdrColumn) {
        String generateSQLCode = super.generateSQLCreateIndexColumn(mpdrColumn);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
