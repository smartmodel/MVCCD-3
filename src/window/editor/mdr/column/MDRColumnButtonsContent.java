package window.editor.mdr.column;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRColumnButtonsContent extends PanelButtonsContent {


    public MDRColumnButtonsContent(MDRColumnButtons MDRColumnButtons) {
        super(MDRColumnButtons);
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
