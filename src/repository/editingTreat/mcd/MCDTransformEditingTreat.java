package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDElement;
import mcd.interfaces.IMCDCompliant;
import mcd.interfaces.IMCDModel;
import mcd.services.MCDElementService;
import md.MDElement;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import transaction.services.TransactionService;

import java.awt.*;

public abstract class MCDTransformEditingTreat extends MCDCompliantEditingTreat {

    public void treatTransform(Window owner, IMCDModel imcdModel) {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("transform.mcdtomldr.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()} );
        resultat.startTransaction(message);

        message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addAll(imcdModel.treatCompliant());
        if (resultat.isWithElementFatal()) {
            message = MessagesBuilder.getMessagesProperty("compliant.mcd.error",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        } else {
            message = MessagesBuilder.getMessagesProperty("compliant.mcd.ok",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
            resultat.addAll(imcdModel.treatTransform());
        }
        TransactionService.treatFinishTransaction(owner, (MVCCDElement) imcdModel, resultat,
                getPropertyTheElement(), "transform.mcdrtomldr.ok", "transform.mcdtomldr.abort") ;


    }


}
