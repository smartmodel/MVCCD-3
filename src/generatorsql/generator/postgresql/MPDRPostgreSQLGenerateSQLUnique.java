package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLUnique;
import mpdr.MPDRUnique;

public class MPDRPostgreSQLGenerateSQLUnique extends MPDRGenerateSQLUnique {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL ;
    public MPDRPostgreSQLGenerateSQLUnique(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateUnique(MPDRUnique mpdrUnique, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateUnique(mpdrUnique);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
