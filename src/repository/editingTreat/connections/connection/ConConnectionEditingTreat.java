package repository.editingTreat.connections.connection;

import main.MVCCDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import utilities.window.DialogMessage;

import java.awt.*;

public abstract class ConConnectionEditingTreat extends EditingTreat {

    public boolean treatDelete(Window owner, MVCCDElement element) {
        if (element.getChilds().size()>0){
            String message = MessagesBuilder.getMessagesProperty("editor.con.connection.delete.with.connector");
            DialogMessage.showError(owner, message) ;
            return false;
        } else {
            return super.treatDelete(owner, element);
        }
    }
}

