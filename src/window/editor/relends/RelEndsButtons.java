package window.editor.relends;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class RelEndsButtons extends PanelButtons {


    public RelEndsButtons(RelEndsEditorBtn relEndsEditor) {
        super(relEndsEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new RelEndsButtonsContent(this);
    }
}
