package repository.editingTreat.mcd;

import m.MElement;
import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDElement;
import mcd.MCDPackage;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.model.ModelEditor;
import window.editor.model.ModelInputContent;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MCDPackageEditingTreat extends EditingTreat {


    @Override
    protected ArrayList<String> checkCompliant(MVCCDElement mvccdElement) {
        return null;
    }

    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new ModelInputContent(element, DialogEditor.PACKAGE);
    }

    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new ModelEditor(owner , (IMCDContContainer) parent, (IMCDContainer) element,
                mode, DialogEditor.PACKAGE);
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.package";
    }



}
