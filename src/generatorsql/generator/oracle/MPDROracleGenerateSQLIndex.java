package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLIndex;
import generatorsql.generator.MPDRGenerateSQLIndexColumn;
import mpdr.MPDRIndex;

public class MPDROracleGenerateSQLIndex extends MPDRGenerateSQLIndex {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL ;
    public MPDROracleGenerateSQLIndex(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateIndex(MPDRIndex mpdrIndex, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateIndex(mpdrIndex);

        return generateSQLCode;
    }

    @Override
    protected MPDRGenerateSQLIndexColumn getMPDRGenerateSQLIndexColumn() {
        return new MPDROracleGenerateSQLIndexColumn(mpdrOracleGenerateSQL);
    }

    public String generateSQLConsolidationDropIndex(MPDRIndex mpdrIndex) {
        String generateSQLCode = super.generateSQLConsolidationDropIndex(mpdrIndex);

        return generateSQLCode;
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }

}
