package window.editor.model;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class ModelButtonsContent extends PanelButtonsContent {


    public ModelButtonsContent(ModelButtons modelButtons) {

        super(modelButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        int scope = ((ModelEditor) getEditor()).getScope();
        if (scope == DialogEditor.MODEL) {
            MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel((MCDContModels) parent);
            return mcdModel;
        }
        if (scope == DialogEditor.PACKAGE) {
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
