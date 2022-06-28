package generatorsql.generator.oracle;

import generatorsql.generator.*;
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

    public String generateSQLConsolidationDropTable(MPDRTable mpdrTable){
        String generateSQLCode = super.generateSQLConsolidationDropTable(mpdrTable);
        return generateSQLCode;
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
