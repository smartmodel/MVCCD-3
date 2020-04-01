package window.editor.relation.association;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.*;
import newEditor.PanelButtonsContent;
import org.apache.commons.lang.StringUtils;
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
        System.out.println(mcdEntityFrom.getName());

        MCDEntity mcdEntityTo = inputContent.getMCDEntityTo();
        MCDContEndRels mcdContEndRelsTo = mcdEntityTo.getMCDContRelations();
        System.out.println(mcdEntityTo.getName());

        MCDAssociation mcdAssociation = MVCCDElementFactory.instance().createMCDAssociation(
                mcdRelations, mcdContEndRelsFrom, mcdContEndRelsTo);
        return mcdAssociation;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }


}
