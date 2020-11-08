package window.editor.operation.constraint.unicity.nid;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContConstraints;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import repository.editingTreat.mcd.MCDUniqueEditingTreat;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;
import window.editor.operation.constraint.unicity.UnicityButtons;
import window.editor.operation.constraint.unicity.UnicityButtonsContent;
import window.editor.operation.constraint.unicity.UnicityEditor;

public class NIDButtonsContent extends UnicityButtonsContent {


    public NIDButtonsContent(UnicityButtons unicityButtons) {
        super(unicityButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
            return MVCCDElementFactory.instance().createMCDNID((MCDContConstraints)parent);
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


    @Override
    public void actionApply(MVCCDElement mvccdElementMaster){
        new MCDNIDEditingTreat().treatUpdate(getEditor().getOwner(),
                mvccdElementMaster);
    }


}
