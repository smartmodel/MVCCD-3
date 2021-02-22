package window.editor.mddatatype;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class MDDatatypeButtonsContent extends PanelButtonsContent {


    public MDDatatypeButtonsContent(MDDatatypeButtons MDDatatypeButtons) {
        super(MDDatatypeButtons);
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
