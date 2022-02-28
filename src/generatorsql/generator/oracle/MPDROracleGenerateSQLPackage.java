package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLPackage;
import mpdr.tapis.MPDRPackage;

public class MPDROracleGenerateSQLPackage extends MPDRGenerateSQLPackage {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLPackage(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreatePackage(MPDRPackage mpdrPackage) {
        String generateSQLCode = super.generateSQLCreatePackage(mpdrPackage);
        return generateSQLCode;
    }



    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
