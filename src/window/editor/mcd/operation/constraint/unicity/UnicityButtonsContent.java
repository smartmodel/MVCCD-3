package window.editor.mcd.operation.constraint.unicity;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;

public abstract class UnicityButtonsContent extends PanelButtonsContent {


    public UnicityButtonsContent(UnicityButtons unicityButtons) {
        super(unicityButtons);
    }


    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }

    protected abstract void actionApply(MVCCDElement mvccdElementMaster);

}

