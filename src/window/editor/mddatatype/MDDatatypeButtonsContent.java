package window.editor.mddatatype;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

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
