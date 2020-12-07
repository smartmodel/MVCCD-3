package repository.editingTreat.mcd;

import exceptions.TransformMCDException;
import main.MVCCDElement;
import mcd.MCDContModels;
import mcd.MCDModel;
import repository.editingTreat.EditingTreat;
import utilities.Trace;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDContModelsEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return null;
    }

    @Override
    // retourne "the.modèle" car le message a trait au modèle implicite
    // A voir si cela s'avère imprécis!
    protected String getPropertyTheElement() {
        return "the.container.models";
    }

    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDContModels mcdContModels = (MCDContModels) mvccdElement;
        ArrayList<String> resultat = mcdContModels.treatCompliant();
        super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        return resultat;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        MCDContModels mcdContModels = (MCDContModels) mvccdElement;
        ArrayList<String> resultat = new ArrayList<String>();
        try {
            resultat = mcdContModels.treatCompliant();
            if (resultat.size() > 0) {
                super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
            } else {
                resultat = mcdContModels.treatTransform();
                super.treatTransformFinishMessages(owner, mvccdElement, resultat);
            }
            return resultat;
        } catch (TransformMCDException e){
            DialogMessage.showError(owner, e.getMessage());
            return resultat;
        }
    }

}
