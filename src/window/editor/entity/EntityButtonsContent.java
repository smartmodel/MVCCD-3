package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import preferences.Preferences;
import utilities.window.editor.PanelButtonsContent;
import utilities.window.DialogMessage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntityButtonsContent extends PanelButtonsContent  {


    public EntityButtonsContent(EntityButtons entityButtons) {
        super(entityButtons);
    }

    @Override
    protected void updateMCDElement() {
        MCDEntity mcdEntity = (MCDEntity) getEditor().getMvccdElement();
        getEditorContent().saveDatas(mcdEntity);
    }

    @Override
    protected MVCCDElement createMVCCDElement() {
        JTextField entityName = getEditorContent().getEntityName();
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(getEditor().getMvccdElement());
        getEditorContent().saveDatas(mcdEntity);
        return mcdEntity;
    }


    private EntityInputContent getEditorContent(){
        return  (EntityInputContent) getEditor().getInput().getPanelContent();
    }


    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }

    @Override
    protected String getHelpFileName() {
        return Preferences.FILE_HELP_ENTITY_NAME;
    }


}
