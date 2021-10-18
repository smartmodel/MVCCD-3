package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationConnexions extends MVCCDElement {


    public MVCCDElementApplicationConnexions(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("application.connexions.name"));
    }


}
