package window.editor.relation.association;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class AssociationButtonsContent extends PanelButtonsContent {


    public AssociationButtonsContent(AssociationButtons associationButtons) {

        super(associationButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        AssociationInputContent inputContent = (AssociationInputContent)  getEditor().getInput().getInputContent();

        MCDEntity mcdEntityFrom = inputContent.getMCDEntityFrom();
        MCDEntity mcdEntityTo = inputContent.getMCDEntityTo();

        MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(
                (MCDContRelations)parent, mcdEntityFrom, mcdEntityTo);
        return mcdAssociation;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }




}
