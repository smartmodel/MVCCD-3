package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.entity.compliant.EntCompliantEditorBtn;
import window.editor.mcd.entity.compliant.EntCompliantInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDEntCompliantEditingTreat extends EditingTreat {


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
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        ArrayList<String> resultat = mcdEntity.treatCompliant();
        //super.treatCompliantBefore(owner, mvccdElement, resultat);
        return resultat;
    }

    @Override
    public ArrayList<String> treatTransform(Window owner, MVCCDElement mvccdElement) {
        return null;
    }


}
