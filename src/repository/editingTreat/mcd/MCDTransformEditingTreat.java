package repository.editingTreat.mcd;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import mcd.interfaces.IMCDModel;
import messages.MessagesBuilder;
import treatment.services.TreatmentService;

import java.awt.*;

public abstract class MCDTransformEditingTreat extends MCDCompliantEditingTreat {

    public void treatTransform(Window owner, IMCDModel imcdModel) {
        boolean ok = true;
        String message = MessagesBuilder.getMessagesProperty("transform.mcdtomldr.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()} );
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdModel.getName()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        ok = imcdModel.treatCompliant();
        if (!ok) {
            message = MessagesBuilder.getMessagesProperty("compliant.mcd.error",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
        } else {
            message = MessagesBuilder.getMessagesProperty("compliant.mcd.ok",
                    new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                            imcdModel.getName()});
            ViewLogsManager.printMessage(message, WarningLevel.INFO);
            ok = imcdModel.treatTransform();
        }
        TreatmentService.treatmentFinish(owner, (MVCCDElement) imcdModel, ok,
                getPropertyTheElement(), "transform.mcdrtomldr.ok", "transform.mcdtomldr.abort") ;
    }


}
