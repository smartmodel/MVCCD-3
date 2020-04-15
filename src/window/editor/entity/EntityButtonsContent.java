package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import mcd.MCDEntity;
import utilities.window.editor.PanelButtonsContent;
import preferences.Preferences;
import project.ProjectElement;

public class EntityButtonsContent extends PanelButtonsContent {


    public EntityButtonsContent(EntityButtons entityButtons) {
        super(entityButtons);
    }


    @Override
    protected MVCCDElement createNewMVCCDElement(MVCCDElement parent) {
        return MVCCDElementFactory.instance().createMCDEntity((ProjectElement)parent);
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
