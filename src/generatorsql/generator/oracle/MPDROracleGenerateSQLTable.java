package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLColumn;
import generatorsql.generator.MPDRGenerateSQLPK;
import generatorsql.generator.MPDRGenerateSQLTable;
import mpdr.MPDRTable;

public class MPDROracleGenerateSQLTable extends MPDRGenerateSQLTable {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    public MPDROracleGenerateSQLTable(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTable(MPDRTable mpdrTable) {
        String generateSQLCode = super.generateSQLCreateTable(mpdrTable);
        return generateSQLCode ;
    }

    @Override
    protected MPDRGenerateSQLPK getMPDRGenerateSQLPK() {
        return new MPDROracleGenerateSQLPK(mpdrOracleGenerateSQL) ;
    }

    @Override
    protected MPDRGenerateSQLColumn getMPDRGenerateSQLColumn() {
        return new MPDROracleGenerateSQLColumn(mpdrOracleGenerateSQL);
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }
}
