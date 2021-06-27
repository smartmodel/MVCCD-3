package test.entites;

import mcd.MCDContAttributes;
import mcd.MCDContEntities;
import mcd.MCDEntity;
import repository.editingTreat.mcd.MCDCompliantEditingTreat;
import test.entites.onglets.contenus.attributs.NewAttributsInput;
import test.entites.onglets.contenus.attributs.NewAttributsInputContent;
import test.entites.onglets.contenus.general.NewGeneraliteInputContent;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelInputContent;

import java.awt.*;

public class EntiteOngletsTreat  extends MCDCompliantEditingTreat {

    @Override
    protected PanelInputContent[] getPanelInputContents(MCDEntity mcdEntity, NewAttributsInput newAttributsInput) {
        PanelInputContent[] pnlContent = new PanelInputContent[2];
        pnlContent[0] = new NewGeneraliteInputContent(mcdEntity);
        pnlContent[1] = new NewAttributsInputContent(newAttributsInput);
        return pnlContent;
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditor(Window owner,
                                           MCDEntity mcdEntity, MCDContEntities mcdContEntities,
                                           String mode) {
        return new EntiteOnglets(owner, mcdEntity, mcdContEntities, mode,
                new EntiteOngletsTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }
}
