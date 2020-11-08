package window.editor.operation.constraint.unicity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContConstraints;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import utilities.window.editor.DialogEditor;
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

