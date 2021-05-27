package window.editor.mdr.unique;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRUniqueButtonsContent extends PanelButtonsContent {


    public MDRUniqueButtonsContent(MDRUniqueButtons MDRUniqueButtons) {
        super(MDRUniqueButtons);
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
