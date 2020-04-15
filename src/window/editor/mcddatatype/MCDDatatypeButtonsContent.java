package window.editor.mcddatatype;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class MCDDatatypeButtonsContent extends PanelButtonsContent {


    public MCDDatatypeButtonsContent(MCDDatatypeButtons mcdDatatypeButtons) {
        super(mcdDatatypeButtons);
    }




    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_MCDDATATYPE_NAME;
    }

    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return null;
    }


}
