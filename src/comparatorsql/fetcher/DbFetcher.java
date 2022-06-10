package comparatorsql.fetcher;

import comparatorsql.elementASupprimer.DbOracleStructure;
import comparatorsql.RecuperationConnectionDB;
import connections.ConConnection;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.MVCCDElementFactory;
import mpdr.MPDRModel;
import mpdr.MPDRTable;

public class DbFetcher {
/*
    private final Connection connection;
    private final ConConnection conConnection;
    private DatabaseMetaData databaseMetaData;
    private String schemaDB;
    private MPDRModel mpdrModel;

    public DbFetcher() throws SQLException {
        RecuperationConnectionDB recuperationConnectionDB = new RecuperationConnectionDB();
        this.connection = recuperationConnectionDB.getConnection();
        this.conConnection = recuperationConnectionDB.getConConnection();
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }

    public DbFetcher(Connection connection, ConConnection conConnection) throws SQLException {
        this.connection = connection;
        this.conConnection = conConnection;
        this.databaseMetaData = this.connection.getMetaData();
        this.schemaDB = this.conConnection.getUserName();
        this.mpdrModel = MVCCDElementFactory.instance().createMPDRModelOracle(null);
    }

 */
/*
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
 */
}
