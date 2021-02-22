package window.editor.mcd.mcdtargets;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MCDTargetsButtons extends PanelButtons {


    public MCDTargetsButtons(MCDTargetsEditor MCDTargetsEditor) {
        super(MCDTargetsEditor);
        //super.setButtonsContent (new AttributesButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MCDTargetsButtonsContent(this);
    }
}
