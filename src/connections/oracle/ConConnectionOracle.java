package connections.oracle;

import connections.ConConnection;
import connections.ConDB;
import connections.services.ConnectionsService;
import main.MVCCDElement;
import repository.editingTreat.connections.connection.ConConnectionEditingTreat;
import repository.editingTreat.connections.connection.ConConnectionOracleEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorOracleEditingTreat;

public class ConConnectionOracle extends ConConnection {

    public ConConnectionOracle(MVCCDElement parent) {
        super(parent, ConDB.ORACLE);
    }

    public ConConnectionOracle(MVCCDElement parent, String name) {
        super(parent, name, ConDB.ORACLE);
    }

    @Override
    public String getResourceURL() {
        return ConnectionsService.getResourceURL(ConDB.ORACLE, getHostName(), getPort(), getConIDDBName(), getDbName());
    }

    @Override
    public ConConnectionEditingTreat getConConnectionEditingTreat() {
        return new ConConnectionOracleEditingTreat();
    }

    @Override
    public ConConnectorEditingTreat getConConnectorEditingTreat() {
        return new ConConnectorOracleEditingTreat();
    }
}
