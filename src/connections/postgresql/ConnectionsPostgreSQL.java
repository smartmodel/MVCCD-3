package connections.postgresql;

import connections.ConDB;
import connections.ConnectionsDB;
import main.MVCCDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.connections.connection.ConConnectionPostgreSQLEditingTreat;

public class ConnectionsPostgreSQL extends ConnectionsDB {


    public ConnectionsPostgreSQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.postgresql"), ConDB.POSTGRESQL);
    }


    @Override
    public EditingTreat getConConnectionEditingTreat() {
        return new ConConnectionPostgreSQLEditingTreat();
    }
}
