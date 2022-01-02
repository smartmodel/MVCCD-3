package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLEmptySchema;

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

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}