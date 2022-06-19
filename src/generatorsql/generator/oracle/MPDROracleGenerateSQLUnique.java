package generatorsql.generator.oracle;

import generatorsql.MPDRGenerateSQLUtil;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLUnique;
import mpdr.MPDRUnique;
import preferences.Preferences;

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

    public String generateSQLCreateUniqueConsolidation(MPDRUnique mpdrUnique) {
        String generateSQLCode =  super.generateSQLCreateUniqueConsolidation(mpdrUnique);
        return generateSQLCode;
    }

    public String generateSQLDropUniqueConsolidation(MPDRUnique mpdrUnique) {
        String generateSQLCode =  super.generateSQLDropUniqueConsolidation(mpdrUnique);
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
