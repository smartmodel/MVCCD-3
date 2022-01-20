package window.editor.mdr.mpdr.trigger;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MPDRTriggerButtonsContent extends PanelButtonsContent {


    public MPDRTriggerButtonsContent(MPDRTriggerButtons MPDRTriggerButtons) {
        super(MPDRTriggerButtons);
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
