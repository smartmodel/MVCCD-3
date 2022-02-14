package generatorsql.generator.postgresql;

import generatorsql.generator.*;
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
    protected MPDRGenerateSQLUnique getMPDRGenerateSQLUnique() {
        return new MPDRPostgreSQLGenerateSQLUnique(mpdrPostgreSQLGenerateSQL);
    }

    @Override
    protected MPDRGenerateSQLPK getMPDRGenerateSQLPK() {
        return new MPDRPostgreSQLGenerateSQLPK(mpdrPostgreSQLGenerateSQL) ;
    }

    @Override
    protected MPDRGenerateSQLTableColumn getMPDRGenerateSQLColumn() {
        return new MPDRPostgreSQLGenerateSQLTableColumn(mpdrPostgreSQLGenerateSQL);
    }

    @Override
    protected MPDRGenerateSQLCheck getMPDRGenerateSQLCheck() {
        return new MPDRPostgreSQLGenerateSQLCheck(mpdrPostgreSQLGenerateSQL);
    }

    @Override
    public MPDRGenerateSQL getMPDRGenerateSQL() {
        return mpdrPostgreSQLGenerateSQL;
    }
}
