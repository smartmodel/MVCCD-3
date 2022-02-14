package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLCheck;
import mpdr.MPDRCheck;

public class MPDRPostgreSQLGenerateSQLCheck extends MPDRGenerateSQLCheck {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL ;
    public MPDRPostgreSQLGenerateSQLCheck(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateCheck(MPDRCheck mpdrCheck, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateCheck(mpdrCheck);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
