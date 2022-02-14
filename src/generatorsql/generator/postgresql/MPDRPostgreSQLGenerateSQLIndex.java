package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLIndex;
import generatorsql.generator.MPDRGenerateSQLIndexColumn;
import mpdr.MPDRIndex;

public class MPDRPostgreSQLGenerateSQLIndex extends MPDRGenerateSQLIndex {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL ;
    public MPDRPostgreSQLGenerateSQLIndex(MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrPostgreSQLGenerateSQL;
    }

    public String generateSQLCreateIndex(MPDRIndex mpdrIndex, String tabsApplicable) {
        String generateSQLCode = super.generateSQLCreateIndex(mpdrIndex);

        return generateSQLCode;
    }

    @Override
    protected MPDRGenerateSQLIndexColumn getMPDRGenerateSQLIndexColumn() {
        return new MPDRPostgreSQLGenerateSQLIndexColumn(mpdrPostgreSQLGenerateSQL);
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }

}
