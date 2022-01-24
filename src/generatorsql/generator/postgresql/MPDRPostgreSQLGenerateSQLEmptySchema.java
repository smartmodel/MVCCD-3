package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLEmptySchema;
import preferences.Preferences;

public class MPDRPostgreSQLGenerateSQLEmptySchema extends MPDRGenerateSQLEmptySchema {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;

    public MPDRPostgreSQLGenerateSQLEmptySchema(MPDRPostgreSQLGenerateSQL mpdrOracleGenerateSQ) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrOracleGenerateSQ;
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
        return mpdrPostgreSQLGenerateSQL;
    }

    @Override
    protected String getEmptySchema() {
        return Preferences.MPDRPOSTGRESQL_EMPTY_SCHEMA;
    }

}