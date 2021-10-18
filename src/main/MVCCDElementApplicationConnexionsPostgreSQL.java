package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationConnexionsPostgreSQL extends MVCCDElement {


    public MVCCDElementApplicationConnexionsPostgreSQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.postgresql"));
    }


}
