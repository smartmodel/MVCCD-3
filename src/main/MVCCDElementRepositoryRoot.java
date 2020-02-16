package main;

import main.MVCCDElement;
import messages.MessagesBuilder;

public class MVCCDElementRepositoryRoot extends MVCCDElement {


    public MVCCDElementRepositoryRoot() {
        super(null, MessagesBuilder.getMessagesProperty("repository.root.name"));
    }

    @Override
    public String baliseXMLBegin() {
        return null;
    }

    @Override
    public String baliseXMLEnd() {
        return null;
    }
}
