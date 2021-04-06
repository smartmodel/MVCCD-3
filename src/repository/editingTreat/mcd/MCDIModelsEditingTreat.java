package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDModel;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreatCompliant;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;

import java.awt.*;

public abstract class MCDIModelsEditingTreat extends EditingTreatCompliant {


    public void treatTransform(Window owner, IMCDModel imcdModel) {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("transform.mcdtomldr.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()} );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addAll(imcdModel.treatCompliant());
        if (resultat.isWithElementFatal()) {
            message = MessagesBuilder.getMessagesProperty("dialog.compliant.error",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
        } else {
            message = MessagesBuilder.getMessagesProperty("dialog.compliant.ok",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            resultat.add(new ResultatElement(message, ResultatLevel.INFO));
            resultat.addAll(imcdModel.treatTransform());
        }
        super.treatTransformFinishMessages(owner, (MVCCDElement) imcdModel, resultat);
    }


}
