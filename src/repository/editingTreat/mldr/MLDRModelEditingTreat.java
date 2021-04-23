package repository.editingTreat.mldr;

import main.MVCCDElement;
import mdr.MDRModel;
import messages.MessagesBuilder;
import mldr.MLDRModel;
import preferences.PreferencesManager;
import repository.editingTreat.EditingTreat;
import resultat.Resultat;
import resultat.ResultatElement;
import resultat.ResultatLevel;
import treatment.services.TreatmentService;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;

import java.awt.*;

public class MLDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
                new MLDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.logical";
    }

    public void treatTransform(Window owner, MVCCDElement mvccdElement) {
        MLDRModel mldrModel = (MLDRModel) mvccdElement;

        Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("transform.mldrtompdr.start",
                new String[] {
                        MessagesBuilder.getMessagesProperty(PreferencesManager.instance().preferences().getMLDRTOMPDR_DB()),
                        MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                        mvccdElement.getName()} );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        //TODO-1 Contrôle de conformité à prévoir !
        resultat.addResultat(mldrModel.treatTransform());

        TreatmentService.treatmentFinish(owner, mvccdElement, resultat,
                getPropertyTheElement(), "transform.mldrtompdr.ok", "transform.mldrtompdr.abort") ;

    }

}
