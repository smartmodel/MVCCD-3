package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFK;
import mpdr.MPDRFK;

public class MPDROracleGenerateSQLFK extends MPDRGenerateSQLFK {

    MPDRGenerateSQL mpdrGenerateSQL ;

    public MPDROracleGenerateSQLFK(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super(mpdrOracleGenerateSQL);
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = super.generateSQLFK(mpdrfk);
        return generateSQLCode ;}


}
