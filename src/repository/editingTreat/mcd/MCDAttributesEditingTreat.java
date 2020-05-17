package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.attributes.AttributesEditor;

import java.awt.*;
import java.util.ArrayList;

public class MCDAttributesEditingTreat extends EditingTreat {

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return null;
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new AttributesEditor(owner , parent, (MCDContAttributes)element, mode,
                new MCDAttributeEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return null;
    }


    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        ArrayList<String> resultat = new ArrayList<String>();
        return resultat;
    }
}
