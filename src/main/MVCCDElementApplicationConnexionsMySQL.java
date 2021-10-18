package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationConnexionsMySQL extends MVCCDElement {


    public MVCCDElementApplicationConnexionsMySQL(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.mysql"));
    }


}
