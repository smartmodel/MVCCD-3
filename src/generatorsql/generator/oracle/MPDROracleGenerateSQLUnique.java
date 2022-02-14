package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLUnique;
import mpdr.MPDRUnique;

public class MPDROracleGenerateSQLUnique extends MPDRGenerateSQLUnique {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLUnique(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateUnique(MPDRUnique mpdrUnique, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateUnique(mpdrUnique);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
