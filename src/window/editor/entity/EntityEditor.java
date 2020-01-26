package window.editor.entity;

import main.MVCCDElement;
import main.MVCCDWindow;
import mcd.MCDEntities;
import mcd.MCDEntity;
import messages.MessagesBuilder;
import preferences.Preferences;
import utilities.window.editor.DialogEditor;

import javax.swing.tree.DefaultMutableTreeNode;

public class EntityEditor extends DialogEditor {



    public EntityEditor(MVCCDWindow mvccdWindow, DefaultMutableTreeNode node, String mode)  {
        super(mvccdWindow);
        String title="";
        super.setMode(mode);
        super.setNode(node);
        // Formulaire en modification (depuis une entité)
        if ( node.getUserObject() instanceof MCDEntity) {
            MCDEntity mcdEntity = (MCDEntity) node.getUserObject();
            super.setMvccdElement(mcdEntity);
            title = MessagesBuilder.getMessagesProperty("editor.entity.update", new String[]{
                    mcdEntity.getName()});
        }
        // Formulaire en création (depuis une racine d'entités)
        if ( node.getUserObject() instanceof MCDEntities) {
            MCDEntities mcdEntities = (MCDEntities) node.getUserObject();
            super.setMvccdElement(mcdEntities);
            title = MessagesBuilder.getMessagesProperty("editor.entity.new");
        }
        super.setTitle(title);
        super.setSize(Preferences.ENTITY_WINDOW_WIDTH, Preferences.ENTITY_WINDOW_HEIGHT);
        super.setInput(new EntityInput(this));
        super.setButtons (new EntityButtons(this));

        super.start();
    }

    @Override
    public void adjustTitle() {
        MCDEntity mcdEntity = (MCDEntity) super.getNode().getUserObject();
        String title = MessagesBuilder.getMessagesProperty("editor.entity.update", new String[]{
                mcdEntity.getName()});
        super.setTitle(title);
    }
}
