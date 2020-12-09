package window.editor.mcd.operation.constraint.unicity.unique;


import utilities.window.editor.PanelButtonsContent;
import window.editor.mcd.operation.constraint.unicity.UnicityButtons;
import window.editor.mcd.operation.constraint.unicity.UnicityEditor;

public class UniqueButtons extends UnicityButtons {


    public UniqueButtons(UnicityEditor unicityEditor) {
        super(unicityEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new UniqueButtonsContent(this);
    }
}
