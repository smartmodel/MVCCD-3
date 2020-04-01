package main;

import messages.MessagesBuilder;

public class MVCCDElementApplicationMDDatatypes extends MVCCDElement {


    public MVCCDElementApplicationMDDatatypes(MVCCDElement parent) {
        super(parent, MessagesBuilder.getMessagesProperty("application.mddatatypes.name"));
    }

    @Override
    public String getNameTree() {
        return null;
    }
}
