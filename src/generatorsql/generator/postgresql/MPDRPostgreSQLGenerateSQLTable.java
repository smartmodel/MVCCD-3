package generatorsql.generator.postgresql;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLColumn;
import generatorsql.generator.MPDRGenerateSQLPK;
import generatorsql.generator.MPDRGenerateSQLTable;
import mpdr.MPDRTable;

public class MPDRPostgreSQLGenerateSQLTable extends MPDRGenerateSQLTable {

    MPDRPostgreSQLGenerateSQL mpdrPostgreSQLGenerateSQL;
    public MPDRPostgreSQLGenerateSQLTable(MPDRPostgreSQLGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrPostgreSQLGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTable(MPDRTable mpdrTable) {
        String generateSQLCode = super.generateSQLCreateTable(mpdrTable);
        return generateSQLCode ;
    }

    @Override
    protected MPDRGenerateSQLPK getMPDRGenerateSQLPK() {
        return new MPDRPostgreSQLGenerateSQLPK(mpdrPostgreSQLGenerateSQL) ;
    }

    @Override
    protected MPDRGenerateSQLColumn getMPDRGenerateSQLColumn() {
        return new MPDRPostgreSQLGenerateSQLColumn(mpdrPostgreSQLGenerateSQL);
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }
}
