package comparatorsql;

import connections.ConConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;
import main.MVCCDElement;
import main.MVCCDElementFactory;
import mdr.MDRTable;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

public class DbFetcher2 {

    private final static Logger LOGGER = Logger.getLogger(DbOracleStructure.class.getName());
    private final Connection connection;
    private final ConConnection conConnection;
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private MVCCDElement mvccdElement = MVCCDElementFactory.instance().createMLDRContTables(null, 1);
    private MPDRModel mpdrModel;

    public DbFetcher2() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }

    public DbFetcher2(Connection connection, ConConnection conConnection) throws SQLException {
        this.connection = connection;
        this.conConnection = conConnection;
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }

    public DbOracleStructure getDatabaseStructure() {
        try {
            DbOracleStructure dbOracleStructure = new DbOracleStructure();
            this.loadTables(this.connection.getMetaData(), this.conConnection.getDbName(),
                this.conConnection.getUserName(), dbOracleStructure);

            return dbOracleStructure;
        } catch (Exception ignore) {
            System.out.println();
        }
        System.out.println();
        return null;
    }

    public void loadTables(DatabaseMetaData metaData, String databaseName, String schemaName,
        DbOracleStructure dbOracleStructure) throws SQLException {
        String[] types = {"TABLE"};

        try (ResultSet result = metaData.getTables(databaseName, schemaName, "%", types)) {
            while (result.next()) {
                MPDRTable mpdrTable = MVCCDElementFactory.instance()
                    .createMPDROracleTable(this.mpdrModel.getMPDRContTables(), null);
                MDRTable mdrTable = MVCCDElementFactory.instance()
                    .createMLDRTable(this.mpdrModel.getMPDRContTables(), null);
                mpdrTable.setName(result.getString("TABLE_NAME"));
                    /*
                Table table = new Table(databaseName, tableName);   // on met DatabaseName car MySQL n'utilise pas le schéma
                table.getColumns(metaData, databaseName, schemaName, tableName);
                table.getPrimaryKeys(metaData, databaseName, schemaName, tableName);
                table.getUniqueConstraint(metaData, databaseName, schemaName, tableName);*/

                this.mpdrModel.getMPDRTables().add(mpdrTable);
            }
        }
        LOGGER.info("Table count: " + dbOracleStructure.getTables().size());
    }
}
