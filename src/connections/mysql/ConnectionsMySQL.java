package connections.mysql;

import connections.ConDB;
import connections.ConnectionsDB;
import exceptions.CodeApplException;
import main.MVCCDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;

public class ConnectionsMySQL extends ConnectionsDB {


    public ConnectionsMySQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.mysql"), ConDB.MYSQL);
    }


    @Override
    public EditingTreat getConConnectionEditingTreat() {
        throw new CodeApplException("La gestion des connexions pour MySQL n'est pas développée");
    }
}
