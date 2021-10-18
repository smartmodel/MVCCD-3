package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationConnexionsOracle extends MVCCDElement {


    public MVCCDElementApplicationConnexionsOracle(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("con.db.oracle"));
    }


}
