package window.editor.mcd.operation.constraint.unicity.nid;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContConstraints;
import preferences.Preferences;
import repository.editingTreat.mcd.MCDNIDEditingTreat;
import window.editor.mcd.operation.constraint.unicity.UnicityButtons;
import window.editor.mcd.operation.constraint.unicity.UnicityButtonsContent;

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

    //#MAJ 2021-07-31 Spéficité d'un élément transitoire
    /*
    @Override
    public void actionApply(MVCCDElement mvccdElementMaster){
        new MCDNIDEditingTreat().treatUpdate(getEditor().getOwner(),
                mvccdElementMaster);
    }

     */


}
