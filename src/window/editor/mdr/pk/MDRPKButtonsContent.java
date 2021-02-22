package window.editor.mdr.pk;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRPKButtonsContent extends PanelButtonsContent {


    public MDRPKButtonsContent(MDRPKButtons MDRPKButtons) {
        super(MDRPKButtons);
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
