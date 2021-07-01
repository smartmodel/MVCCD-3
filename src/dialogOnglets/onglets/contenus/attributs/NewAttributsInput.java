package dialogOnglets.onglets.contenus.attributs;

import dialogOnglets.EntiteOnglets;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class NewAttributsInput extends PanelInput {
    final String name = "Attributs";

    public NewAttributsInput(EntiteOnglets entiteOnglets) {
        super(entiteOnglets);
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new NewAttributsInputContent(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
