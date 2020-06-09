package window.editor.relation.genspec;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class GenSpecButtonsContent extends PanelButtonsContent {


    public GenSpecButtonsContent(GenSpecButtons genSpecButtons) {

        super(genSpecButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        GenSpecInputContent inputContent = (GenSpecInputContent)  getEditor().getInput().getInputContent();

        MCDEntity mcdEntityGen = inputContent.getMCDEntityGen();
        //MCDContEndRels mcdContEndRelsFrom = mcdEntityGen.getMCDContEndRels();

        MCDEntity mcdEntitySpec = inputContent.getMCDEntitySpec();
        //MCDContEndRels mcdContEndRelsTo = mcdEntitySpec.getMCDContEndRels();

        MCDGeneralization mcdGeneralization = MVCCDElementFactory.instance().createMCDGeneralization(
                (MCDContRelations)parent, mcdEntityGen, mcdEntitySpec);
        return mcdGeneralization;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_GENERALIZATION_NAME;
    }




}
