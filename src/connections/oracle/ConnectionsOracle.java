package connections.oracle;

import connections.ConDB;
import connections.ConnectionsDB;
import main.MVCCDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.connections.connection.ConConnectionOracleEditingTreat;

public class ConnectionsOracle extends ConnectionsDB {

    public ConnectionsOracle(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.oracle"), ConDB.ORACLE);
    }

    @Override
    public EditingTreat getConConnectionEditingTreat() {
        return new ConConnectionOracleEditingTreat();
    }
}
