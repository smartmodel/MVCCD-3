package generatorsql.generator.oracle;

import generatorsql.generator.MPDRGenerateSQL;
import generatorsql.generator.MPDRGenerateSQLCheck;
import generatorsql.generator.MPDRGenerateSQLPK;
import generatorsql.generator.MPDRGenerateSQLTable;
import generatorsql.generator.MPDRGenerateSQLTableColumn;
import generatorsql.generator.MPDRGenerateSQLUnique;
import mpdr.MPDRTable;
import mpdr.interfaces.IMPDRTable;

public class MPDROracleGenerateSQLTable extends MPDRGenerateSQLTable {

    MPDROracleGenerateSQL mpdrOracleGenerateSQL;
    public MPDROracleGenerateSQLTable(MPDROracleGenerateSQL mpdrOracleGenerateSQL) {
        super();
        this.mpdrOracleGenerateSQL = mpdrOracleGenerateSQL;
    }

    public String generateSQLCreateTable(MPDRTable mpdrTable) {
        String generateSQLCode = super.generateSQLCreateTable((IMPDRTable) mpdrTable);
        return generateSQLCode ;
    }

    @Override
    protected MPDRGenerateSQLUnique getMPDRGenerateSQLUnique() {
        return new MPDROracleGenerateSQLUnique(mpdrOracleGenerateSQL);
    }

    @Override
    protected MPDRGenerateSQLPK getMPDRGenerateSQLPK() {
        return new MPDROracleGenerateSQLPK(mpdrOracleGenerateSQL) ;
    }

    @Override
    protected MPDRGenerateSQLTableColumn getMPDRGenerateSQLColumn() {
        return new MPDROracleGenerateSQLTableColumn(mpdrOracleGenerateSQL);
    }

    @Override
    protected MPDRGenerateSQLCheck getMPDRGenerateSQLCheck() {
        return new MPDROracleGenerateSQLCheck(mpdrOracleGenerateSQL);
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrOracleGenerateSQL;
    }
}
