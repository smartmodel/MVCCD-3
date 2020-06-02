package window.editor.operation.constraint.constraints;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class ConstraintsButtons extends PanelButtons {


    public ConstraintsButtons(ConstraintsEditor constraintsEditor) {
        super(constraintsEditor);
    }

    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new ConstraintsButtonsContent(this);
    }
}
