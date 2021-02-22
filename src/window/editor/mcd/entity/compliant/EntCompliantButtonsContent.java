package window.editor.mcd.entity.compliant;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.editor.PanelButtonsContent;

public class EntCompliantButtonsContent extends PanelButtonsContent {


    public EntCompliantButtonsContent(EntCompliantButtons entCompliantButtons) {
        super(entCompliantButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return MVCCDElementFactory.instance().createMCDEntity((ProjectElement)parent);
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }

    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }


}
