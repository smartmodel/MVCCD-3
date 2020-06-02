package window.editor.operation.constraint.constraints;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class ConstraintsInput extends PanelInput {

    public ConstraintsInput(ConstraintsEditor constraintsEditor) {
        super(constraintsEditor);
    }


    @Override
    protected PanelInputContent createInputContentCustom() {
        return new ConstraintsInputContent(this);
    }
}
