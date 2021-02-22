package repository.editingTreat;

import console.Console;
import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import org.apache.commons.lang.StringUtils;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class EditingTreatTransform extends EditingTreat{


    public abstract void treatCompliant(
            Window owner, MVCCDElement mvccdElement);

    public abstract void treatTransform(
            Window owner, MVCCDElement mvccdElement)  ;



    protected void treatCompliantFinishMessages(Window owner, MVCCDElement mvccdElement, ArrayList<String> messages) {
        treatFinishMessages(owner, mvccdElement, messages,"dialog.compliant.ok", "dialog.compliant.error") ;
    }

    protected void treatTransformFinishMessages(Window owner, MVCCDElement mvccdElement, ArrayList<String> messages) {
        treatFinishMessages(owner, mvccdElement, messages,"dialog.transform.ok", "dialog.transform.error") ;
    }

    protected void treatFinishMessages(Window owner, MVCCDElement mvccdElement, ArrayList<String> messages,
                    String propertyOk , String propertyError ) {
        String message = "";
        MDElement mdElement = (MDElement) mvccdElement;
        String mdElementName = mdElement.getName();
        if (mdElement instanceof MCDElement){
            mdElementName = ((MCDElement) mdElement).getNamePath(MCDElementService.PATHSHORTNAME);
        }
        if (messages.size() == 0 ){
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    new String[]{messageElement, mdElementName});
        }

        if (messages.size() > 0 ){
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, mdElementName});
            message = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");

        }
        //TODO-1 Erreur si < 0

        //Console.printMessage(message);
        DialogMessage.showOk(owner, message);
    }
}
