package repository.editingTreat.mpdr;

import generatesql.window.GenerateSQLWindow;
import main.MVCCDElement;
import mdr.MDRModel;
import messages.MessagesBuilder;
import mpdr.MPDRModel;
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

public class MPDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
                new MPDRModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.physical";
    }

    public void treatGenerate(MVCCDElement mvccdElement) {
        MPDRModel mpdrModel = (MPDRModel) mvccdElement;

        GenerateSQLWindow dialog = new GenerateSQLWindow(mpdrModel);
        /*Resultat resultat = new Resultat();
        String message = MessagesBuilder.getMessagesProperty("generatesql.mpdrtosql.start",
            new String[] {
                MessagesBuilder.getMessagesProperty(getPropertyTheElement()),
                mvccdElement.getName()
            }
        );
        resultat.add(new ResultatElement(message, ResultatLevel.INFO));

        resultat.addResultat(mpdrModel.treatGenerate());

        TreatmentService.treatmentFinish(owner, mvccdElement, resultat,
            getPropertyTheElement(), "generatesql.mpdrtosql.ok", "generatesql.mpdrtosql.abort");*/
    }
}
