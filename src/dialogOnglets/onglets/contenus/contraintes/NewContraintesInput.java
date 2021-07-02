package dialogOnglets.onglets.contenus.contraintes;

import dialogOnglets.EntiteOnglets;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class NewContraintesInput extends PanelInput {
    final String name = "Contraintes";

    public NewContraintesInput(EntiteOnglets entiteOnglets) {
        super(entiteOnglets);
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new NewContraintesInputContent(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
