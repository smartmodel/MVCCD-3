package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLPackage;
import mpdr.tapis.MPDRPackage;

public class MPDROracleGenerateSQLPackage extends MPDRGenerateSQLPackage {


    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLPackage(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLConsolidationDropPackageAndBody(MPDRPackage mpdrPackage) {
        String generateSQLCode = super.generateSQLConsolidationDropPackageAndBody(mpdrPackage);
        return generateSQLCode;
    }
    public String generateSQLConsolidationDropPackageAndBody(String packageName) {
        String generateSQLCode = super.generateSQLConsolidationDropPackageAndBody(packageName);
        return generateSQLCode;
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
