package window.editor.mcd.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.editor.DialogEditor;
import utilities.window.editor.PanelButtonsContent;

import java.awt.event.ActionEvent;

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

    protected void createContent(){
        super.createContent();
        btnApply.setVisible(true);
    }
}
