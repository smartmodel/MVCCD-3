package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.EditingTreat;
import repository.editingTreat.EditingTreatTransform;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.entity.EntityEditor;
import window.editor.mcd.entity.EntityInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDEntityEditingTreat extends EditingTreatTransform {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new EntityInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new EntityEditor(owner, (MCDContEntities) parent, (MCDEntity) element, mode,
                new MCDEntityEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }

    @Override
    public void treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        ArrayList<String> resultat = mcdEntity.treatCompliant();
        super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
    }

    @Override
    public void treatTransform(Window owner, MVCCDElement mvccdElement) {
    }


}
