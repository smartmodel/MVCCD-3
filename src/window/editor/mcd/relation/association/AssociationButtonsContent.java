package window.editor.mcd.relation.association;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDAssociation;
import mcd.MCDContRelations;
import mcd.MCDEntity;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

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
