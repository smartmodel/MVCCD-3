package repository.editingTreat.mcd;

import exceptions.TransformMCDException;
import main.MVCCDElement;
import mcd.MCDModel;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.EditingTreatTransform;
import utilities.window.DialogMessage;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.model.ModelEditor;
import window.editor.mcd.model.ModelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDModelEditingTreat extends EditingTreatTransform {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new ModelInputContent(element, ModelEditor.MODEL);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ModelEditor(owner , (IMCDContContainer) parent, (IMCDContainer) element,
                mode, ModelEditor.MODEL, new MCDModelEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.conceptual";
    }

    @Override
    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDModel mcdModel = (MCDModel) mvccdElement;
        ArrayList<String> resultat = mcdModel.treatCompliant();
        super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
    }

    @Override
    public  void treatTransform(Window owner, MVCCDElement mvccdElement)   {
        MCDModel mcdModel = (MCDModel) mvccdElement;
        ArrayList<String> resultat = new ArrayList<String>();
        resultat = mcdModel.treatCompliant();
        if (resultat.size() > 0) {
                super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        } else {
                resultat = mcdModel.treatTransform();
                super.treatTransformFinishMessages(owner, mvccdElement, resultat);
        }
    }

}
