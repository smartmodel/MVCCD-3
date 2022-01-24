package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLSequence;
import mpdr.MPDRSequence;

public class MPDRPostgreSQLGenerateSQLSequence extends MPDRGenerateSQLSequence {


    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLSequence(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateSequence(MPDRSequence mpdrSequence) {
        String generateSQLCode = super.generateSQLCreateSequence(mpdrSequence);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
