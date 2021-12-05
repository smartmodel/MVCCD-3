package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDModel;
import messages.MessagesBuilder;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import treatment.services.TreatmentService;

import java.awt.*;

public abstract class MCDTransformEditingTreat extends MCDCompliantEditingTreat {

    public void treatTransform(Window owner, IMCDModel imcdModel) {
        Resultat resultat = new Resultat();
        resultat.setPrintImmediatelyForResultat(true);
        String message = MessagesBuilder.getMessagesProperty("transform.mcdtomldr.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()} );
        resultat.add(new ResultatElement (message, ResultatLevel.INFO));

        message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(imcdModel.treatCompliant());
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
            resultat.addResultat(imcdModel.treatTransform());
        }
        TreatmentService.treatmentFinish(owner, (MVCCDElement) imcdModel, resultat,
                getPropertyTheElement(), "transform.mcdrtomldr.ok", "transform.mcdtomldr.abort") ;
    }


}
