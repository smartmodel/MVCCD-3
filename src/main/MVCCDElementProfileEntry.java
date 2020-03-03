package main;

import messages.MessagesBuilder;

public class MVCCDElementProfileEntry extends MVCCDElement {

    public MVCCDElementProfileEntry(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("profile.entry.name"));
    }


}
