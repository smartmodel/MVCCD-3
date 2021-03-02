package repository.editingTreat;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import utilities.window.DialogMessage;

import java.awt.*;
import java.util.ArrayList;

public abstract class EditingTreatTransform extends EditingTreat{

    /**
     * Effectue le contrôle de conformité en deux phases:
     *  1. Lancement du contrôle de saisie de l'éditeur graphique pour l'élément à contrôler ;
     *  2. Lancement d'une méthode spécifique à l'élément à contrôler, qui effectue alors un contrôle prenant en compte tous les éléments du projet utilisateur.
     */
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
