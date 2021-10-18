package window.editor.naming;

import main.MVCCDElement;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class NamingButtonsContent extends PanelButtonsContent {


    public NamingButtonsContent(NamingButtons NamingButtons) {
        super(NamingButtons);
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
