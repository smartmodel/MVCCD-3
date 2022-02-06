package window.editor.mdr.check;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRCheckButtonsContent extends PanelButtonsContent {


    public MDRCheckButtonsContent(MDRCheckButtons MDRCheckButtons) {
        super(MDRCheckButtons);
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
