package window.editor.mdr.mpdr.sequence;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MPDRSequenceButtonsContent extends PanelButtonsContent {


    public MPDRSequenceButtonsContent(MPDRSequenceButtons MPDRSequenceButtons) {
        super(MPDRSequenceButtons);
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
