package consolidationMpdrDb;

import connections.*;
import connections.oracle.ConConnectionOracle;
import connections.postgresql.ConConnectionPostgreSQL;
import exceptions.CodeApplException;
import main.*;

import java.sql.Connection;
import java.util.ArrayList;

public class RecuperationConnectionDB {

    private Connection connection;
    private ConConnection conConnection;

    public RecuperationConnectionDB() {
        MVCCDElementRepositoryRoot repositoryRoot = new MVCCDElementRepositoryRoot();
        MVCCDElementRepositoryGlobal repositoryGlobal = new MVCCDElementRepositoryGlobal(repositoryRoot);
        MVCCDElementApplicationConnections repositoryApplicationConnections = MVCCDFactory.instance().createRepositoryApplicationConnections(repositoryGlobal);
        ArrayList<ConnectionsDB> connectionsDB = repositoryApplicationConnections.getConnectionsDB();
        ConConnection conConnection = null;
        ConDB conDB = null;
        for (ConnectionsDB db : connectionsDB) {
            if (!db.getChilds().isEmpty()) {
                //ConElement extend ConnectionsDB
                ConElement db1 = db;
                conDB = db1.getConDB();
                if (conDB == ConDB.ORACLE) {
                    conConnection = (ConConnectionOracle) db.getChilds().get(0);
                } else if (conDB == ConDB.POSTGRESQL) {
                    conConnection = (ConConnectionPostgreSQL) db.getChilds().get(0);
                } else{
                    throw new CodeApplException("Il faut ajouter la création de la connexion pour " + conDB.getText());
                }
            }
        }

        this.conConnection = conConnection;
        this.connection = ConManager.createConnection(this.conConnection);
/*
        try {
            DbOracleStructure dbOracleStructure = new DbOracleStructure(connection.getMetaData(), conConnection.getUserName());
            //dbTable.getAllTable();
            dbOracleStructure.getTables(connection.getMetaData(), conConnection.getDbName(), conConnection.getUserName());

            dbOracleStructure.getTableName();
        } catch (Exception ignore) {
            System.out.println();
        }
        System.out.println();

 */
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public ConConnection getConConnection() {
        return conConnection;
    }

    public void setConConnection(ConConnection conConnection) {
        this.conConnection = conConnection;
    }
}
