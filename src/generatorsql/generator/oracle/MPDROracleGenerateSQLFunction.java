package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFunction;
import mpdr.tapis.MPDRFunction;

public class MPDROracleGenerateSQLFunction extends MPDRGenerateSQLFunction {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    public MPDROracleGenerateSQLFunction(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateFunction(MPDRFunction mpdrFunction) {
        String generateSQLCode = super.generateSQLCreateFunction(mpdrFunction);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
