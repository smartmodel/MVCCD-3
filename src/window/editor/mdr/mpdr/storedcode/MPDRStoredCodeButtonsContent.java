package window.editor.mdr.mpdr.storedcode;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MPDRStoredCodeButtonsContent extends PanelButtonsContent {


    public MPDRStoredCodeButtonsContent(MPDRStoredCodeButtons MPDRStoredCodeButtons) {
        super(MPDRStoredCodeButtons);
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
