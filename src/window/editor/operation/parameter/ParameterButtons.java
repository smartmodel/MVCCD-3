package window.editor.operation.parameter;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class ParameterButtons extends PanelButtons {

    public ParameterButtons(ParameterEditor parameterEditor) {
        super(parameterEditor);
    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {
        return new ParameterButtonsContent(this);
    }
}
