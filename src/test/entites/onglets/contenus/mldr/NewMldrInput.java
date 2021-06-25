package test.entites.onglets.contenus.mldr;

import test.entites.EntiteOnglets;
import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class NewMldrInput extends PanelInput {
    final String name = "MLD-R";

    public NewMldrInput(EntiteOnglets entiteOnglets) {
        super(entiteOnglets);
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new NewMldrInputContent(this);
    }

    @Override
    public String getName() {
        return name;
    }
}
