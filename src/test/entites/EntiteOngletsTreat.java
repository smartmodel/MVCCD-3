package test.entites;

import main.MVCCDElement;
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
    protected PanelInputContent[] getPanelInputContents(MCDEntity mcdEntity) {
        PanelInputContent[] panelInputContents = new PanelInputContent[6];
        panelInputContents[0] = new NewGeneraliteInputContent(mcdEntity);
        return panelInputContents;
    }

    /**
     * Fournit à la classe ancêtre l'éditeur d'entité à utiliser. C'est cet éditeur qui s'affiche à l'utilisateur lors
     * de la création d'une nouvelle entité.
     */
    @Override
    protected DialogEditor getDialogEditorOnglets(Window owner,
                                           MCDContEntities mcdContEntities, MCDEntity mcdEntity,
                                                  String mode) {
        return new EntiteOnglets(owner, mcdContEntities, mcdEntity, mode,
                new EntiteOngletsTreat());
    }

    @Override
    protected String getPropertyTheElement() {
        return "the.entity";
    }
}
