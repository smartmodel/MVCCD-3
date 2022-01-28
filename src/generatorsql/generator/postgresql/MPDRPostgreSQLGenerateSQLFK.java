package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFK;
import mpdr.MPDRFK;

public class MPDRPostgreSQLGenerateSQLFK extends MPDRGenerateSQLFK {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL ;

    public MPDRPostgreSQLGenerateSQLFK(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = super.generateSQLFK(mpdrfk);
        return generateSQLCode ;}

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }


}
