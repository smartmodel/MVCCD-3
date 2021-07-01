package dialogOnglets.onglets.contenus.relations;

import dialogOnglets.EntiteOnglets;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class NewRelationsInput extends PanelInput {
    final String name = "Relations";

    public NewRelationsInput(EntiteOnglets entiteOnglets) {
        super(entiteOnglets);
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new NewRelationsInputContent(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
