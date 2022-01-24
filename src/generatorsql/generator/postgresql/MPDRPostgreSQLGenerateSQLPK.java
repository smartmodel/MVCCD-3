package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLPK;
import mpdr.MPDRTable;

public class MPDRPostgreSQLGenerateSQLPK extends MPDRGenerateSQLPK {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLPK(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreatePK(MPDRTable mpdrTable) {
        String generateSQLCode = super.generateSQLCreatePK(mpdrTable);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
