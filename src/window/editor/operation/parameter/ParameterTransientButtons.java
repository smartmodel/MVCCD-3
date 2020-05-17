package window.editor.operation.parameter;

import utilities.window.editor.PanelButtons;
import utilities.window.editor.PanelButtonsContent;

public class ParameterTransientButtons extends ParameterButtons {

    public ParameterTransientButtons(ParameterTransientEditor parameterEditor) {
        super(parameterEditor);
    }


    @Override
    protected PanelButtonsContent createButtonsContentCustom() {

        return new ParameterTransientButtonsContent(this);
    }
}
