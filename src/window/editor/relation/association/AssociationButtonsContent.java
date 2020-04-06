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
    protected MVCCDElement createNewMVCCDElement() {
        MCDRelations mcdRelations = (MCDRelations) getEditor().getMvccdElementParent();

        AssociationInputContent inputContent = (AssociationInputContent)  getEditor().getInput().getInputContent();

        MCDEntity mcdEntityFrom = inputContent.getMCDEntityFrom();
        MCDContEndRels mcdContEndRelsFrom = mcdEntityFrom.getMCDContRelations();

        MCDEntity mcdEntityTo = inputContent.getMCDEntityTo();
        MCDContEndRels mcdContEndRelsTo = mcdEntityTo.getMCDContRelations();

        MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(
                mcdRelations, mcdContEndRelsFrom, mcdContEndRelsTo);
        return mcdAssociation;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }


}
