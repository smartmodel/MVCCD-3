package connections;

import main.MVCCDElement;
import messages.MessagesBuilder;

public class ConnectionsPostgreSQL extends ConnectionsDB {


    public ConnectionsPostgreSQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.postgresql"), ConDB.POSTGRESQL);
    }


}
