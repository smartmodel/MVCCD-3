package window.editor.operation.constraint.unique;


import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class UniqueButtons extends PanelButtons {


    public UniqueButtons(UniqueEditor uniqueEditor) {
        super(uniqueEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new UniqueButtonsContent(this);
    }
}
