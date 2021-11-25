package window.editor.mdr.model;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public abstract class MDRModelButtonContent extends PanelButtonsContent {


    public MDRModelButtonContent(MDRModelButtons MDRModelButtons) {
        super(MDRModelButtons);
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
