package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import mcd.interfaces.IMCDCompliant;
import messages.MessagesBuilder;
import resultat.Resultat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.entity.compliant.EntCompliantEditorBtn;
import window.editor.mcd.entity.compliant.EntCompliantInputContent;

import java.awt.*;

public class MCDEntCompliantEditingTreat extends MCDCompliantEditingTreat {


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

}
