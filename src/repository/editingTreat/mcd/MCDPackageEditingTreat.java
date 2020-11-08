package repository.editingTreat.mcd;

import main.MVCCDElement;
import mcd.MCDPackage;
import mcd.interfaces.IMCDContContainer;
import mcd.interfaces.IMCDContainer;
import repository.editingTreat.EditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.model.ModelEditor;
import window.editor.model.ModelInputContent;

import java.awt.*;
import java.util.ArrayList;

public class MCDPackageEditingTreat extends EditingTreat {

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

    @Override
    public ArrayList<String> treatCompliant(Window owner, MVCCDElement mvccdElement) {
        MCDPackage mcdPackage = (MCDPackage) mvccdElement;
        ArrayList<String> resultat = mcdPackage.treatCompliant();
        super.treatCompliantFinishMessages(owner, mvccdElement, resultat);
        return resultat;
    }


}
