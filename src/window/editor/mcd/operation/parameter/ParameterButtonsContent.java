package window.editor.mcd.operation.parameter;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.editor.PanelButtonsContent;

public class ParameterButtonsContent extends PanelButtonsContent  {


    public ParameterButtonsContent(ParameterButtons parameterButtons) {

        super(parameterButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return MVCCDElementFactory.instance().createMCDParameter((ProjectElement) parent);
    }

    private ParameterInputContent getEditorContent(){
        return  (ParameterInputContent) getEditor().getInput().getPanelContent();
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_PARAMETER_NAME;
    }


}
