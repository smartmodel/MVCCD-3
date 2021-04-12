package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.model.ModelEditor;
import window.editor.mcd.model.ModelInputContent;

import java.awt.*;

public class MCDModelEditingTreat extends MCDTransformEditingTreat {


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

}
