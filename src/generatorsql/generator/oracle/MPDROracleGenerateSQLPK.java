package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLPK;
import mpdr.MPDRPK;
import mpdr.MPDRTable;

public class MPDROracleGenerateSQLPK extends MPDRGenerateSQLPK {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLPK(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreatePK(MPDRTable mpdrTable, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreatePK(mpdrTable);

        return generateSQLCode;
    }

    public String generateSQLAddPKConsolidation(MPDRPK mpdrPk) {
        String generateSQLCode = super.generateSQLAddPKConsolidation(mpdrPk);
        return generateSQLCode;
    }

    public String generateSQLDropPKConsolidation(MPDRPK mpdrPk) {
        String generateSQLCode = super.generateSQLDropPKConsolidation(mpdrPk);
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
