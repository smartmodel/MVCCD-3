package window.editor.mcd.operation.parameter;


import utilities.window.editor.PanelInput;
import utilities.window.editor.PanelInputContent;

public class ParameterInput extends PanelInput {

    public ParameterInput(ParameterEditor parameterEditor) {
        super(parameterEditor);
        //super.setInputContent( new ProjectInputContent(this));
    }

    @Override
    protected PanelInputContent createInputContentCustom() {
        return new ParameterInputContent(this);
    }
}
