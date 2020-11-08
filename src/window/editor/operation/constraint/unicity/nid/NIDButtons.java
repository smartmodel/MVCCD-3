package window.editor.operation.constraint.unicity.nid;


import utilities.window.editor.PanelButtonsContent;
import window.editor.operation.constraint.unicity.UnicityButtons;
import window.editor.operation.constraint.unicity.UnicityButtonsContent;
import window.editor.operation.constraint.unicity.UnicityEditor;

public class NIDButtons extends UnicityButtons {


    public NIDButtons(UnicityEditor unicityEditor) {
        super(unicityEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new NIDButtonsContent(this);
    }
}
