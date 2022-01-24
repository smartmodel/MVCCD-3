package connections.oracle;

import connections.ConConnection;
import connections.ConConnector;
import connections.ConDB;
import repository.editingTreat.connections.connector.ConConnectorEditingTreat;
import repository.editingTreat.connections.connector.ConConnectorOracleEditingTreat;

public class ConConnectorOracle extends ConConnector {

    public ConConnectorOracle(ConConnection parent, String name) {
        super(parent, name, ConDB.ORACLE);
    }

    public ConConnectorOracle(ConConnection parent) {
        super(parent, ConDB.ORACLE);
    }

    @Override
    public ConConnectorEditingTreat getConConnectorEditingTreat() {
        return new ConConnectorOracleEditingTreat() ;
    }
}
