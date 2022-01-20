package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLDynamicCode;
import mpdr.tapis.MPDRTrigger;

public class MPDROracleGenerateSQLDynamicCode extends MPDRGenerateSQLDynamicCode {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLDynamicCode(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger, String generateSQLCode) {
        super.generateSQLCodeDynamic(mpdrTrigger, generateSQLCode);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
