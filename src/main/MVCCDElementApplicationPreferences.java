package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationPreferences extends MVCCDElement {


    public MVCCDElementApplicationPreferences(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("application.preferences.name"));
    }


}
