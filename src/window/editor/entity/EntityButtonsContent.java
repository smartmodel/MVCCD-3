package window.editor.entity;

import main.MVCCDElementFactory;
import main.MVCCDManager;
import mcd.MCDEntity;
import preferences.Preferences;
import utilities.window.ButtonsContent;
import utilities.window.DialogMessage;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntityButtonsContent extends ButtonsContent implements ActionListener {

    private EntityButtons entityButtons;

    public EntityButtonsContent(EntityButtons entityButtons) {
        super(entityButtons);
        this.entityButtons = entityButtons;
        getBtnOk().addActionListener(this);
        //getBtnOk().setEnabled(false);
        getBtnCancel().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == super.getBtnOk()) {
            if (getEditorContent().checkDatas()) {
                if (getEntityWindow().getMode().equals(EntityWindow.NEW)) {
                    createEntity();
                    getEntityWindow().dispose();
                }
                if (getEntityWindow().getMode().equals(EntityWindow.UPDATE)) {
                    updateEntity();
                    getEntityWindow().dispose();
                }
            } else {
                DialogMessage.showErrorEditor(entityButtons.getEntityWindow());
            }

        }
        if (source == super.getBtnCancel()) {
            getEntityWindow().dispose();
        }
    }



    private EntityEditorContent getEditorContent(){
        return  (EntityEditorContent) entityButtons.getEntityWindow().getEditor().getContent();
    }
    private void createEntity() {
        JTextField entityName = getEditorContent().getEntityName();
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(getEntityWindow().getMvccdElement());
        getEditorContent().saveDatas(mcdEntity);
        DefaultMutableTreeNode node = MVCCDManager.instance().getRepository().addMVCCDElement(mcdEntity,getEntityWindow().getNode());
        // Affichage du nouveau noeud
        MVCCDManager.instance().getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(node.getPath()));
    }

    private void updateEntity() {
        MCDEntity mcdEntity = (MCDEntity) getEntityWindow().getMvccdElement();
        getEditorContent().saveDatas(mcdEntity);
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) MVCCDManager.instance().getWinRepositoryContent().getTree().getModel();
        defaultTreeModel.nodeChanged(getEntityWindow().getNode());
    }

    private EntityWindow getEntityWindow(){
        return entityButtons.getEntityWindow();
    }

    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }
}
