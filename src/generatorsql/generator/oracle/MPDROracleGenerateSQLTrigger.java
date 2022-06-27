package generatorsql.generator.oracle;

import generatorsql.MPDRGenerateSQLUtil;
import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLTrigger;
import mpdr.tapis.MPDRTrigger;
import preferences.Preferences;

public class MPDROracleGenerateSQLTrigger extends MPDRGenerateSQLTrigger {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLTrigger(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTrigger(MPDRTrigger mpdrTrigger) {
        String generateSQLCode = super.generateSQLCreateTrigger(mpdrTrigger);
        return generateSQLCode;
    }

    public String generateSQLDropTriggerConsolidation(MPDRTrigger mpdrTrigger){
        String generateSQLCode = super.generateSQLDropTriggerConsolidation(mpdrTrigger);
        return generateSQLCode;
    }

    public String generateSQLDropTrigger(String dbTriggerNotInTable){
        String generateSQLCode = super.generateSQLDropTrigger(dbTriggerNotInTable);
        return generateSQLCode;
    }


    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
