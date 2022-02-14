package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLCheck;
import mpdr.MPDRCheck;

public class MPDROracleGenerateSQLCheck extends MPDRGenerateSQLCheck {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLCheck(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateCheck(MPDRCheck mpdrCheck, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateCheck(mpdrCheck);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
