package repository.editingTreat.mcd;

import main.MVCCDElement;
import main.MVCCDManager;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import mcd.MCDRelation;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;
import window.editor.mcd.entity.EntityEditor;
import window.editor.mcd.entity.EntityInputContent;

import java.awt.*;

public class MCDEntityEditingTreat extends MCDCompliantEditingTreat {


    @Override
    protected PanelInputContent getPanelInputContent(MVCCDElement element) {
        return new EntityInputContent(element);
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner, MVCCDElement parent, MVCCDElement element, String mode) {
        return new EntityEditor(owner, (MCDContEntities) parent, (MCDEntity) element, mode,
                new MCDEntityEditingTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }


}
