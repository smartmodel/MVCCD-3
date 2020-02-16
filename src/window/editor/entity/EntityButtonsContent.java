package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import preferences.Preferences;
import project.Project;
import utilities.window.editor.PanelButtonsContent;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

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
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(getEditor().getMvccdElement());
        saveDatas(mcdEntity);
        return mcdEntity;
    }

    @Override
    protected void completeNewMVCCDElement(MVCCDElement mvccdElement) {
        MVCCDManager.instance().showNewMVCCDElementInRepository(mvccdElement, getEditor());
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
