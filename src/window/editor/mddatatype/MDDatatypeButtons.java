package window.editor.mddatatype;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class MDDatatypeButtons extends PanelButtons {


    public MDDatatypeButtons(MDDatatypeEditor MDDatatypeEditor) {
        super(MDDatatypeEditor);
        //super.setButtonsContent (new MCDDatatypeButtonsContent(this));
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new MDDatatypeButtonsContent(this);
    }
}
