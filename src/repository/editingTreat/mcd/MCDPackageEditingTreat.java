package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import repository.editingTreat.EditingTreatCompliant;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.model.ModelEditor;
import window.editor.mcd.model.ModelInputContent;

import java.awt.*;

public class MCDPackageEditingTreat extends EditingTreatCompliant {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ModelInputContent(element, ModelEditor.PACKAGE);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ModelEditor(owner , (IMCDContContainer) parent, (IMCDContainer) element,
                mode, ModelEditor.PACKAGE, new MCDPackageEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.package";
    }



}
