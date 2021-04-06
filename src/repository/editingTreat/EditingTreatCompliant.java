package repository.editingTreat;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import utilities.Trace;
import utilities.window.DialogMessage;

import java.awt.*;
import java.util.ArrayList;

public abstract class EditingTreatCompliant extends EditingTreat{

    /**
     * Effectue le contrôle de conformité en deux phases:
     *  1. Lancement du contrôle de saisie de l'éditeur graphique pour l'élément à contrôler ;
     *  2. Lancement d'une méthode spécifique à l'élément à contrôler, qui effectue alors un contrôle prenant en compte tous les éléments du projet utilisateur.
     */

    public void treatCompliant(Window owner, IMCDCompliant imcdCompliant) {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdCompliant.getName()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addAll(imcdCompliant.treatCompliant());
        treatCompliantFinishMessages(owner, (MVCCDElement) imcdCompliant, resultat);
    }


    protected void treatCompliantFinishMessages(Window owner, MVCCDElement mvccdElement, Resultat resultat) {
        treatFinishMessages(owner, mvccdElement, resultat,"dialog.compliant.ok", "dialog.compliant.error") ;
    }

    protected void treatTransformFinishMessages(Window owner, MVCCDElement mvccdElement, Resultat resultat) {
        treatFinishMessages(owner, mvccdElement, resultat,"dialog.transform.ok", "dialog.transform.error") ;
    }

    protected void treatFinishMessages(Window owner, MVCCDElement mvccdElement, Resultat resultat,
                    String propertyOk , String propertyError ) {
        String message = "";
        String messageDialog = "";
        MDElement mdElement = (MDElement) mvccdElement;
        String mdElementName = mdElement.getName();
        if (mdElement instanceof MCDElement){
            mdElementName = ((MCDElement) mdElement).getNamePath(MCDElementService.PATHSHORTNAME);
        }
        if (resultat.isWithoutElementFatal() ){
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());
            message = MessagesBuilder.getMessagesProperty(propertyOk,
                    new String[]{messageElement, mdElementName});
            messageDialog = message ;
        }

        if ( resultat.isWithElementFatal() ){
            String messageElement = MessagesBuilder.getMessagesProperty(getPropertyTheElement());
            message = MessagesBuilder.getMessagesProperty(propertyError,
                    new String[]{messageElement, mdElementName});
            messageDialog = message + System.lineSeparator() + MessagesBuilder.getMessagesProperty("dialog.error.console");

        }
        DialogMessage.showOk(owner, messageDialog);
        resultat.add(new ResultatElement(message, ResultatLevel.NO_FATAL));
        ViewLogsManager.newResultat(resultat, WarningLevel.WARNING);
    }
}
