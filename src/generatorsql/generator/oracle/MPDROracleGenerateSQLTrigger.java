package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTrigger;
import mpdr.tapis.MPDRTrigger;

public class MPDROracleGenerateSQLTrigger extends MPDRGenerateSQLTrigger {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLTrigger(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger) {
        String generateSQLCode = super.generateSQLCreateTrigger(mpdrTrigger);
        return generateSQLCode;
    }

    public String generateSQLConsolidationDropTrigger(MPDRTrigger mpdrTrigger){
        String generateSQLCode = super.generateSQLConsolidationDropTrigger(mpdrTrigger);
        return generateSQLCode;
    }

    public String generateSQLConsolidationDropTrigger(String dbTriggerNotInTable){
        String generateSQLCode = super.generateSQLConsolidationDropTrigger(dbTriggerNotInTable);
        return generateSQLCode;
    }


    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
