package window.editor.mdr.table;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public class MDRTableButtonsContent extends PanelButtonsContent {


    public MDRTableButtonsContent(MDRTableButtons MDRTableButtons) {
        super(MDRTableButtons);
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
