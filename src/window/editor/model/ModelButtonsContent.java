package window.editor.model;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.*;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;

public class ModelButtonsContent extends PanelButtonsContent {


    public ModelButtonsContent(ModelButtons modelButtons) {

        super(modelButtons);
    }



    @Override
    protected MVCCDElement createNewMVCCDElement() {
        int scope = ((ModelEditor) getEditor()).getScope();
        if (scope == ModelEditor.MODEL) {
            MCDModels parent = (MCDModels) getEditor().getMvccdElementParent();
            MCDModel mcdModel = MVCCDElementFactory.instance().createMCDModel(parent);
            return mcdModel;
        }
        if (scope == ModelEditor.PACKAGE) {
            MCDElement parent = (MCDElement) getEditor().getMvccdElementParent();
            MCDPackage mcdPackage = MVCCDElementFactory.instance().createMCDPackage(parent);
            return mcdPackage;
        }

        return null;
    }


    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ASSOCIATION_NAME;
    }


}
