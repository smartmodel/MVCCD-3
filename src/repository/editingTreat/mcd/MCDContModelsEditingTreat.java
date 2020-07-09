package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDContModels;
import mcd.MCDPackage;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.model.ModelEditor;
import window.editor.model.ModelInputContent;

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
        super.treatCompliantBefore(owner, mvccdElement, resultat);
        return resultat;
    }

}
