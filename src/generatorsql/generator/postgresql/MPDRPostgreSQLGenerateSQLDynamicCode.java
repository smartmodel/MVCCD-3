package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLDynamicCode;
import mpdr.tapis.MPDRTrigger;

public class MPDRPostgreSQLGenerateSQLDynamicCode extends MPDRGenerateSQLDynamicCode {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLDynamicCode(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger, String generateSQLCode) {
        super.generateSQLCodeDynamic(mpdrTrigger, generateSQLCode);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
