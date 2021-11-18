package connections;

import main.MVCCDElement;
import messages.MessagesBuilder;

public class ConnectionsOracle extends ConnectionsDB {

    public ConnectionsOracle(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.oracle"), ConDB.ORACLE);
    }


}
