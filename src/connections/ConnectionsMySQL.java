package connections;

import main.MVCCDElement;
import messages.MessagesBuilder;

public class ConnectionsMySQL extends ConnectionsDB {


    public ConnectionsMySQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.mysql"), ConDB.MYSQL);
    }


}
