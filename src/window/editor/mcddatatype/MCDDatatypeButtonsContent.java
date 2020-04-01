package window.editor.mcddatatype;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import newEditor.PanelButtonsContent;
import preferences.Preferences;
import project.ProjectElement;

public class MCDDatatypeButtonsContent extends PanelButtonsContent {


    public MCDDatatypeButtonsContent(MCDDatatypeButtons mcdDatatypeButtons) {
        super(mcdDatatypeButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement() {
        return null;
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_MCDDATATYPE_NAME;
    }


}
