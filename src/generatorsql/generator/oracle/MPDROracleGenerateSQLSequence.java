package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLSequence;
import mpdr.MPDRSequence;

public class MPDROracleGenerateSQLSequence extends MPDRGenerateSQLSequence {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLSequence(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode = super.generateSQLCreateSequence(mpdrSequence);
        return generateSQLCode;
    }

    public String generateSQLConsolidationDropSequence(String mpdrSequence) {
        String generateSQLCode = super.generateSQLConsolidationDropSequence(mpdrSequence);
        return generateSQLCode;
    }


    public String generateSQLConsolidationDropSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode = super.generateSQLConsolidationDropSequence(mpdrSequence);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
