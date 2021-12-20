package treatment.services;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import mcd.MCDElement;
import md.MDElement;
import messages.MessagesBuilder;
import utilities.window.DialogMessage;

import java.awt.*;

public class TreatmentService {


    public static void treatmentFinish(Window owner,
                                       MVCCDElement mvccdElement,
                                       boolean ok,
                                       String propertyTheElement,
                                       String propertyOk ,
                                       String propertyError ) {
        String message = "";
        String messageDialog = "";
        MDElement mdElement = (MDElement) mvccdElement;
        String mdElementName = mdElement.getNamePath();
        if (mdElement instanceof MCDElement){
            mdElementName = ((MCDElement) mdElement).getNamePath();
        }

        String messageElement = MessagesBuilder.getMessagesProperty(propertyTheElement);

        if (ok){
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    new String[]{messageElement, mdElementName});
            messageDialog = message;
        } else {
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, mdElementName});
            messageDialog = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        DialogMessage.showOk(owner, messageDialog);
    }


    public static void treatmentFinish(Window owner,
                                       String[] parameters,
                                       boolean ok,
                                       boolean autonomous,
                                       String propertyOk ,
                                       String propertyError ) {
        String message = "";
        String messageDialog = "";

        if (ok){
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    parameters);
            messageDialog = message;
        } else {
            message = MessagesBuilder.getMessagesProperty(propertyError, parameters);
            messageDialog = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");
        }
        ViewLogsManager.printMessage(message, WarningLevel.INFO);
        if (autonomous) {
            DialogMessage.showOk(owner, messageDialog);
        }
    }

}
