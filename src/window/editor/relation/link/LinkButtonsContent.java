package window.editor.relation.link;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class LinkButtonsContent extends PanelButtonsContent {


    public LinkButtonsContent(LinkButtons linkButtons) {

        super(linkButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        LinkInputContent inputContent = (LinkInputContent)  getEditor().getInput().getInputContent();

        MCDEntity mcdEntity = inputContent.getMCDEntity();
        MCDAssociation mcdAssociation= inputContent.getMCDAssociation();

        MCDLink mcdLink = MVCCDElementFactory.instance().createMCDLink(
                (MCDContRelations)parent, mcdEntity, mcdAssociation);
        return mcdLink;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_GENERALIZATION_NAME;
    }




}
