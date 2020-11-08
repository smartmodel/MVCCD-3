package window.editor.operation.constraint.unicity.unique;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;
import window.editor.operation.constraint.unicity.UnicityButtons;
import window.editor.operation.constraint.unicity.UnicityButtonsContent;
import window.editor.operation.constraint.unicity.UnicityEditor;

public class UniqueButtons extends UnicityButtons {


    public UniqueButtons(UnicityEditor unicityEditor) {
        super(unicityEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new UniqueButtonsContent(this);
    }
}
