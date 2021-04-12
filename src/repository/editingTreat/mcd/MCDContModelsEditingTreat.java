package repository.editingTreat.mcd;

import main.MVCCDElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import java.awt.*;

public class MCDContModelsEditingTreat extends MCDTransformEditingTreat {


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

}
