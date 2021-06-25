package test.entites.boutons;

import test.entites.EntiteOnglets;
import utilities.window.editor.PanelButtons;

public class PanelBoutons extends PanelButtons {
    public PanelBoutons(EntiteOnglets entiteOnglets) {
        super(entiteOnglets);
    }

    @Override
    protected Boutons createButtonsContentCustom() {
        return new Boutons(this);
    }
}
