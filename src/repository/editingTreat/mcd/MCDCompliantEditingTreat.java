package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDCompliant;
import messages.MessagesBuilder;
import repository.editingTreat.EditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import treatment.services.TreatmentService;

import java.awt.*;

public abstract class MCDCompliantEditingTreat extends EditingTreat {


    public void treatCompliant(Window owner, IMCDCompliant imcdCompliant) {
        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("compliant.mcd.start",
                new String[] {MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        imcdCompliant.getName()});
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(imcdCompliant.treatCompliant());

        traitementConformite(owner, imcdCompliant, resultat);
    }

    protected void traitementConformite(Window owner, IMCDCompliant imcdCompliant, Resultat resultat){
        TreatmentService.treatmentFinish(owner, (MVCCDElement) imcdCompliant, resultat,
                getPropertyTheElement(), "compliant.mcd.ok", "compliant.mcd.error") ;
    }
}


