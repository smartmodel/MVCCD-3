package window.editor.mdr.parameter;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRParameterButtonsContent extends PanelButtonsContent {


    public MDRParameterButtonsContent(MDRParameterButtons MDRParameterButtons) {
        super(MDRParameterButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return null;
    }



    @Override
    protected String getHelpFileName() {
        return null;
    }

    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }


}
