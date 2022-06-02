package comparatorsql;

import connections.ConConnection;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mdr.MDRTable;
import mpdr.MPDRContTables;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DbFetcher2 {
    private final static Logger LOGGER = Logger.getLogger(DbOracleStructure.class.getName());
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private final Connection connection;
    private final ConConnection conConnection;
    private MVCCDElement mvccdElement = MVCCDElementFactory.instance().createMLDRContTables(null,1);
    private MPDRContTables mpdrContTables;

    public DbFetcher2() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
    }

    public DbOracleStructure getDatabaseStructure() {
        try {
            DbOracleStructure dbOracleStructure = new DbOracleStructure();
            loadTables(this.connection.getMetaData(), this.conConnection.getDbName(), this.conConnection.getUserName(), dbOracleStructure);

            return dbOracleStructure;
        } catch (Exception ignore) {
            System.out.println();
        }
        System.out.println();
        return null;
    }

    public void loadTables(DatabaseMetaData metaData, String databaseName, String schemaName, DbOracleStructure dbOracleStructure) throws SQLException {
        String[] types = {"TABLE"};

        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                MDRTable mdrTable = MVCCDElementFactory.instance().createMLDRTable(mpdrContTables, null);
                mdrTable.setName(result.getString("TABLE_NAME"));
                /*mdrTable.
                Table table = new Table(databaseName, tableName);   // on met DatabaseName car MySQL n'utilise pas le schéma
                table.getColumns(metaData, databaseName, schemaName, tableName);
                table.getPrimaryKeys(metaData, databaseName, schemaName, tableName);
                table.getUniqueConstraint(metaData, databaseName, schemaName, tableName);*/
                mpdrContTables.getMDRTables().add(mdrTable);
            }
        }
        LOGGER.info("Table count: " + dbOracleStructure.getTables().size());
    }
}
