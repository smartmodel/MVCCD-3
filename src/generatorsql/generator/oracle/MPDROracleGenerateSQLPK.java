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

    public String generateSQLConsolidationAddPK(MPDRPK mpdrPk) {
        String generateSQLCode = super.generateSQLConsolidationAddPK(mpdrPk);
        return generateSQLCode;
    }

    public String generateSQLConsolidationDropPK(MPDRPK mpdrPk) {
        String generateSQLCode = super.generateSQLConsolidationDropPK(mpdrPk);
        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
