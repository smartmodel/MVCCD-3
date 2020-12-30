package repository.editingTreat.mldr;

import main.MVCCDElement;
import mdr.MDRModel;
import mldr.MLDRModel;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.EditingTreatTransform;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;

import java.awt.*;
import java.util.ArrayList;

public class MLDRModelEditingTreat extends EditingTreatTransform {


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

    @Override
    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
}

    @Override
    public void treatTransform(Window owner, MVCCDElement mvccdElement) {
        MLDRModel mldrModel = (MLDRModel) mvccdElement;

        /*
        ArrayList<String> resultat = mldrModel.treatCompliant();
        if (resultat.size() > 0) {
            super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        } else {

         */
        mldrModel.treatTransform();
     }

}
