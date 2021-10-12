package treatment.services;

import console.ViewLogsManager;
import console.WarningLevel;
import m.services.MElementService;
import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.window.DialogMessage;

import java.awt.*;
import java.util.ArrayList;

public class TreatmentService {


    public static void treatmentFinish(Window owner,
                                       MVCCDElement mvccdElement,
                                       Resultat resultat,
                                       String propertyTheElement,
                                       String propertyOk ,
                                       String propertyError ) {
        String message = "";
        MDElement mdElement = (MDElement) mvccdElement;
        String mdElementName = mdElement.getName();
        if (mdElement instanceof MCDElement){
            mdElementName = ((MCDElement) mdElement).getNamePath();
        }

        String messageElement = MessagesBuilder.getMessagesProperty(propertyTheElement);

        if ( resultat.isNotError()){
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    new String[]{messageElement, mdElementName});
        } else {
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, mdElementName});
            message += System.lineSeparator();
            message += MessagesBuilder.getMessagesProperty("dialog.error.console",
                    new String[]{messageElement, mdElementName});
        }
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));
        ViewLogsManager.printResultat(resultat);
        DialogMessage.showOk(owner, message);
    }
}
