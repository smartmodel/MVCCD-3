package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContModels;
import repository.editingTreat.EditingTreatTransform;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDContModelsEditingTreat extends EditingTreatTransform {


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
    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDContModels mcdContModels = (MCDContModels) mvccdElement;
        ArrayList<String> resultat = mcdContModels.treatCompliant();
        super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
    }


    @Override
    public void treatTransform(Window owner, MVCCDElement mvccdElement) {
        MCDContModels mcdContModels = (MCDContModels) mvccdElement;
        ArrayList<String> resultat = new ArrayList<String>();
        resultat = mcdContModels.treatCompliant();
        if (resultat.size() > 0) {
                super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        } else {
            resultat = mcdContModels.treatTransform();
            super.treatTransformFinishMessages(owner, mvccdElement, resultat);
        }
    }

}
