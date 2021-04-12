package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDCompliant;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import resultat.Resultat;
import transaction.services.TransactionService;

import java.awt.*;

public abstract class MCDCompliantEditingTreat extends EditingTreat {


    public void treatCompliant(Window owner, IMCDCompliant imcdCompliant) {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdCompliant.getName()});
        resultat.startTransaction(message);

        resultat.addAll(imcdCompliant.treatCompliant());
        TransactionService.treatFinishTransaction(owner, (MVCCDElement) imcdCompliant, resultat,
                getPropertyTheElement(), "compliant.mcd.ok", "compliant.mcd.error") ;
    }
}


