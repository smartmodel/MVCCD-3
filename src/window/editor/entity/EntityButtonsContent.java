package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDContAttributes;
import mcd.MCDEntity;
import preferences.Preferences;
import project.ProjectElement;
import utilities.window.editor.PanelButtonsContent;

public class EntityButtonsContent extends PanelButtonsContent  {


    public EntityButtonsContent(EntityButtons entityButtons) {
        super(entityButtons);
    }

    /*
    @Override
    protected void updateMCDElement() {
        MCDEntity mcdEntity = (MCDEntity) getEditor().getMvccdElement();
        getEditor().getInput().getInputContent().saveDatas(mcdEntity);
    }
    */

    @Override
    protected MVCCDElement createNewMVCCDElement() {
        //JTextField entityName = getEditorContent().getEntityName();
        ProjectElement projectElement = (ProjectElement) getEditor().getMvccdElement();
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(projectElement);
        saveDatas(mcdEntity);
        return mcdEntity;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {
        MCDEntity mcdEntity = (MCDEntity) mvccdElement;
        MCDContAttributes mcdContAttributes = MVCCDElementFactory.instance().createMCDAttributes(mcdEntity,
                Preferences.REPOSITORY_MCD_ATTRIBUTES_NAME);
        MVCCDManager.instance().showNewMVCCDElementInRepository(mcdEntity, getEditor());
     }

/*
    private PrefMCDInputContent getEditorContent(){
        return  (PrefMCDInputContent) getEditor().getInput().getPanelContent();
    }
*/

    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
