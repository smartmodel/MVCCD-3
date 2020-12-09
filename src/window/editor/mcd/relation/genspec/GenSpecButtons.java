package window.editor.mcd.relation.genspec;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class GenSpecButtons extends PanelButtons {


    public GenSpecButtons(GenSpecEditor genSpecEditor) {
        super(genSpecEditor);
        //super.setButtonsContent (new AssociationButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new GenSpecButtonsContent(this);
    }
}
