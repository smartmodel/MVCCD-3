package window.editor.mcd.model;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDContModels;
import mcd.MCDElement;
import mcd.MCDModel;
import mcd.MCDPackage;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;

public class ModelButtonsContent extends PanelButtonsContent {


    public ModelButtonsContent(ModelButtons modelButtons) {

        super(modelButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        int scope = ((ModelEditor) getEditor()).getScope();
        if (scope == ModelEditor.MODEL) {
            MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel((MCDContModels) parent);
            return mcdModel;
        }
        if (scope == ModelEditor.PACKAGE) {
            MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage((MCDElement) parent);
            return mcdPackage;
        }

        return null;
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }




}
