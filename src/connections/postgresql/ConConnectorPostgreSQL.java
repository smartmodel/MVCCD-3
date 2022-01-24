package connections.postgresql;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDB;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorPostgreSQLEditingTreat;

public class ConConnectorPostgreSQL extends ConConnector {

    public ConConnectorPostgreSQL(ConConnection parent, String name) {
        super(parent, name, ConDB.POSTGRESQL);
    }

    public ConConnectorPostgreSQL(ConConnection parent) {
        super(parent, ConDB.POSTGRESQL);
    }

    @Override
    public ConConnectorEditingTreat getConConnectorEditingTreat() {
        return new ConConnectorPostgreSQLEditingTreat() ;
    }
}
