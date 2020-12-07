package repository.editingTreat.mldr;

import main.MVCCDElement;
import mcd.MCDModel;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import mcd.interfaces.IMCDModel;
import mdr.MDRContTables;
import mdr.MDRModel;
import mdr.MDRTable;
import mldr.MLDRModel;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.mdr.MDRTableEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mdr.model.MDRModelEditor;
import window.editor.mdr.table.MDRTableEditor;
import window.editor.model.ModelEditor;
import window.editor.model.ModelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MLDRModelEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new MDRModelEditor(owner, parent, (MDRModel) element, mode,
                new MDRTableEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.model.logical";
    }

    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {

        return new ArrayList<String>();
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        MLDRModel mldrModel = (MLDRModel) mvccdElement;

        /*
        ArrayList<String> resultat = mldrModel.treatCompliant();
        if (resultat.size() > 0) {
            super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        } else {

         */
        ArrayList<String> resultat = mldrModel.treatTransform();
        super.treatTransformFinishMessages(owner, mvccdElement, resultat);

        return resultat;
    }

}
