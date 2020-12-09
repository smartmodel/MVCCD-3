package window.editor.mcd.operation.constraint.unicity.nid;


import utilities.window.editor.PanelButtonsContent;
import window.editor.mcd.operation.constraint.unicity.UnicityButtons;
import window.editor.mcd.operation.constraint.unicity.UnicityEditor;

public class NIDButtons extends UnicityButtons {


    public NIDButtons(UnicityEditor unicityEditor) {
        super(unicityEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new NIDButtonsContent(this);
    }
}
