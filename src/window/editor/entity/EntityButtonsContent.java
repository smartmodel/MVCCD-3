package window.editor.entity;

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

public class EntityButtonsContent extends PanelButtonsContent implements ActionListener {

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
                if (getEditor().getMode().equals(EntityEditor.NEW)) {
                    createEntity();
                    getEditor().dispose();
                }
                if (getEditor().getMode().equals(EntityEditor.UPDATE)) {
                    updateEntity();
                    getEditor().dispose();
                }
            } else {
                DialogMessage.showErrorEditor(entityButtons.getEditor());
            }

        }
        if (source == super.getBtnCancel()) {
            getEditor().dispose();
        }
    }



    private EntityInputContent getEditorContent(){
        return  (EntityInputContent) entityButtons.getEditor().getInput().getContent();
    }
    private void createEntity() {
        JTextField entityName = getEditorContent().getEntityName();
        MCDEntity mcdEntity = MVCCDElementFactory.instance().createMCDEntity(getEditor().getMvccdElement());
        getEditorContent().saveDatas(mcdEntity);
        DefaultMutableTreeNode node = MVCCDManager.instance().getRepository().addMVCCDElement(mcdEntity,getEditor().getNode());
        // Affichage du nouveau noeud
        MVCCDManager.instance().getWinRepositoryContent().getTree().scrollPathToVisible(new TreePath(node.getPath()));
    }

    private void updateEntity() {
        MCDEntity mcdEntity = (MCDEntity) getEditor().getMvccdElement();
        getEditorContent().saveDatas(mcdEntity);
        DefaultTreeModel defaultTreeModel = (DefaultTreeModel) MVCCDManager.instance().getWinRepositoryContent().getTree().getModel();
        defaultTreeModel.nodeChanged(getEditor().getNode());
    }


    @Override
    public Integer getWidthWindow() {
        return Preferences.ENTITY_WINDOW_WIDTH;
    }


}
