package window.editor.mdr.mpdr.view;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MPDRViewButtonsContent extends PanelButtonsContent {


    public MPDRViewButtonsContent(MPDRViewButtons MPDRViewButtons) {
        super(MPDRViewButtons);
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
