package window.editor.mdr.fk;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRFKButtonsContent extends PanelButtonsContent {


    public MDRFKButtonsContent(MDRFKButtons MDRFKButtons) {
        super(MDRFKButtons);
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
