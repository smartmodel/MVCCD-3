package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDAttribute;
import mcd.MCDContAttributes;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.attribute.AttributeEditor;
import window.editor.mcd.attribute.AttributeInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDAttributeEditingTreat extends EditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new AttributeInputContent(element);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new AttributeEditor(owner , (MCDContAttributes) parent, (MCDAttribute) element, mode,
                new MCDAttributeEditingTreat());
    }

    public boolean treatUpdate(Window owner, MVCCDElement element) {
        // Pour tester le contrôle d'ajustement
        //MCDAttribute mcdAttribute = (MCDAttribute) element;
        //mcdAttribute.setList(true);
        return super.treatUpdate(owner, element);
    }

        @Override
    protected String getPropertyTheElement() {
        return "the.attribute";
    }

}
