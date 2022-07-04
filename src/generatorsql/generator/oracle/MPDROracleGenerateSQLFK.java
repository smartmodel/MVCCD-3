package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLFK;
import mpdr.MPDRFK;

public class MPDROracleGenerateSQLFK extends MPDRGenerateSQLFK {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL;

    public MPDROracleGenerateSQLFK(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {

        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLFK(MPDRFK mpdrfk) {
        String generateSQLCode = super.generateSQLFK(mpdrfk);
        return generateSQLCode ;}

    public String generateSQLConsolidationFkToDrop(MPDRFK mpdrfk){
        String generateSQLCode = super.generateSQLConsolidationFkToDrop(mpdrfk);
        return generateSQLCode ;
    }

    public String generateSQLConsolidationAddFKWithDeleteCascadeOption(MPDRFK mpdrfk) {
        String generateSQLCode = super.generateSQLConsolidationAddFKWithDeleteCascadeOption(mpdrfk);
        return generateSQLCode ;
    }
    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }


}
