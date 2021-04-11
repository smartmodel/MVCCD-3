package transaction.services;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import resultat.Resultat;

import java.awt.*;

public class TransactionService {


    public static void treatFinishTransaction(Window owner,
                                          MVCCDElement mvccdElement,
                                          Resultat resultat,
                                          String propertyTheElement,
                                          String propertyOk ,
                                          String propertyError ) {
        String message = "";
        MDElement mdElement = (MDElement) mvccdElement;
        String mdElementName = mdElement.getName();
        if (mdElement instanceof MCDElement){
            mdElementName = ((MCDElement) mdElement).getNamePath(MCDElementService.PATHSHORTNAME);
        }

        if ( resultat.isNotError()){
            String messageElement = MessagesBuilder.getMessagesProperty(propertyTheElement);
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    new String[]{messageElement, mdElementName});
        } else {
            String messageElement = MessagesBuilder.getMessagesProperty(propertyTheElement);
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, mdElementName});
        }
        resultat.finishTransaction(message, owner, false);
    }

}
