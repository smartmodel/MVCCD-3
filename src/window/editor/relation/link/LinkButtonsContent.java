package window.editor.relation.link;

import main.MVCCDElement;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class LinkButtonsContent extends PanelButtonsContent {


    public LinkButtonsContent(LinkButtons linkButtons) {

        super(linkButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement() {

        return null;
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }


}
