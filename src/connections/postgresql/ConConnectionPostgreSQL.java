package connections.postgresql;

import connections.ConConnection;
import connections.ConDB;
import connections.services.ConnectionsService;
import main.MVCCDElement;
import repository.editingTreat.connections.connection.ConConnectionEditingTreat;
import repository.editingTreat.connections.connection.ConConnectionPostgreSQLEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorPostgreSQLEditingTreat;

public class ConConnectionPostgreSQL extends ConConnection {

    public ConConnectionPostgreSQL(MVCCDElement parent) {
        super(parent, ConDB.POSTGRESQL);
    }

    public ConConnectionPostgreSQL(MVCCDElement parent, String name) {
        super(parent, name, ConDB.POSTGRESQL);
    }

    @Override
    public String getResourceURL() {
        return ConnectionsService.getResourceURL(ConDB.POSTGRESQL, getHostName(), getPort(), getConIDDBName(), getDbName());
    }

    @Override
    public ConConnectionEditingTreat getConConnectionEditingTreat() {
        return new ConConnectionPostgreSQLEditingTreat();
    }

    @Override
    public ConConnectorEditingTreat getConConnectorEditingTreat() {
        return new ConConnectorPostgreSQLEditingTreat();
    }
}
