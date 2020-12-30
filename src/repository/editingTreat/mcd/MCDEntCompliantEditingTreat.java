package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.EditingTreatTransform;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.entity.compliant.EntCompliantEditorBtn;
import window.editor.mcd.entity.compliant.EntCompliantInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDEntCompliantEditingTreat extends EditingTreatTransform {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {

        return new EntCompliantInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new EntCompliantEditorBtn(owner, (MCDContEntities) parent, (MCDEntity) element, mode,
                new MCDEntCompliantEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }

    @Override
    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        ArrayList<String> resultat = mcdEntity.treatCompliant();
    }

    @Override
    public void treatTransform(Window owner, MVCCDElement mvccdElement) { }


}
