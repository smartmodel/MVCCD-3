package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLEmptySchema;
import preferences.Preferences;

public class MPDROracleGenerateSQLEmptySchema extends MPDRGenerateSQLEmptySchema {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL;

    public MPDROracleGenerateSQLEmptySchema(MPDROracleGenerateSQL mpdrOracleGenerateSQ) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQ;
    }

    public String generateSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += super.generateSQLEmptySchema();
        return generateSQLCode;
    }

    public String generateExecuteSQLEmptySchema() {
        String generateSQLCode = "";
        generateSQLCode += super.generateExecuteSQLEmptySchema();
        return generateSQLCode;
    }

  @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

    @Override
    protected String getEmptySchema() {
        return Preferences.MPDRORACLE_EMPTY_SCHEMA;
    }

}