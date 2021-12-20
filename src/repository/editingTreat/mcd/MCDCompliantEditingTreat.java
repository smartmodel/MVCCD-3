package repository.editingTreat.mcd;

import console.ViewLogsManager;
import console.WarningLevel;
import main.MVCCDElement;
import mcd.interfaces.IMCDCompliant;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import treatment.services.TreatmentService;

import java.awt.*;

public abstract class MCDCompliantEditingTreat extends EditingTreat {


    public boolean treatCompliant(Window owner, IMCDCompliant imcdCompliant) {
       String message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdCompliant.getName()});
        ViewLogsManager.printMessage(message, WarningLevel.INFO);

        boolean ok = imcdCompliant.treatCompliant();

        TreatmentService.treatmentFinish(owner, (MVCCDElement) imcdCompliant, ok,
                getPropertyTheElement(), "compliant.mcd.ok", "compliant.mcd.error") ;
        return ok ;
    }
}


