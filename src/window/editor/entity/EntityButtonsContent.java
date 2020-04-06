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
    protected MVCCDElement createNewMVCCDElement() {
        //JTextField entityName = getEditorContent().getEntityName();
        ProjectElement projectElement = (ProjectElement) getEditor().getMvccdElementParent();
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(projectElement);
        // saveDatas(mcdEntity);
        return mcdEntity;
    }



    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
