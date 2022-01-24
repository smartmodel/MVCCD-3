package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTrigger;
import mpdr.tapis.MPDRTrigger;

public class MPDRPostgreSQLGenerateSQLTrigger extends MPDRGenerateSQLTrigger {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLTrigger(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger) {
        String generateSQLCode = super.generateSQLCreateTrigger(mpdrTrigger);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
