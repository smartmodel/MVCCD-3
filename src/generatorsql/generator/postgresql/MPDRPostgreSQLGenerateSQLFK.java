package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFK;
import mpdr.MPDRFK;

public class MPDRPostgreSQLGenerateSQLFK extends MPDRGenerateSQLFK {

    MPDRGenerateSQL mpdrGenerateSQL ;

    public MPDRPostgreSQLGenerateSQLFK(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        super(mpdrPostgreSQLGenerateSQL);
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = super.generateSQLFK(mpdrfk);
        return generateSQLCode ;}


}
